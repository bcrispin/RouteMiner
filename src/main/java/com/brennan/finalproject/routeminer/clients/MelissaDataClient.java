package com.brennan.finalproject.routeminer.clients;

import com.brennan.finalproject.routeminer.models.RouteInfo;
import com.brennan.finalproject.routeminer.models.ZipRoutes;
import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class MelissaDataClient {

    /**
     *
     * @param zips
     * @return
     */
    public static List<ZipRoutes> getRouteInfo(List<String> zips) {
        List<ZipRoutes> zipRoutesList  = new ArrayList<>();

        return zipRoutesList;
    }

    /**
     *
     * @param zip
     * @return
     */
    public static ZipRoutes getZipRoute(String zip) {

        ZipRoutes zipRoutes = null;
        String baseUrl = "http://www.melissadata.com/lookups/MapCartS.asp?zip=";

        WebClient client = new WebClient();
        client.getOptions().setCssEnabled(false);
        client.getOptions().setJavaScriptEnabled(false);

        List<RouteInfo> routeInfoList = new ArrayList<>();
        String searchUrl = baseUrl + zip;
        try {
            HtmlPage page = client.getPage(searchUrl);
            HtmlElement table = page.getFirstByXPath("/html/body/div/table[4]/tbody");
            if (table != null) {
                DomNodeList<DomNode> rows = table.getChildNodes();
                for (DomNode row : rows) {
                    List<HtmlElement> tds = row.getByXPath("./td");
                    if (tds.size() == 11 && !(tds.get(0).getFirstChild() instanceof HtmlBold)) {
                        RouteInfo routeInfo = RouteInfo.builder()
                                .routeId(tds.get(0).getFirstChild().toString())
                                .routeType(tds.get(1).getFirstChild().toString())
                                .county(tds.get(2).getFirstChild().getFirstChild().getFirstChild().toString())
                                .businesses(tds.get(3).getFirstChild().toString())
                                .apartments(tds.get(4).getFirstChild().toString())
                                .poBoxes(tds.get(5).getFirstChild().toString())
                                .residentials(tds.get(6).getFirstChild().toString())
                                .totalDeliveries(tds.get(7).getFirstChild().getFirstChild().toString())
                                .householdIncome(tds.get(8).getFirstChild().toString())
                                .propertyValue(tds.get(9).getFirstChild().toString())
                                .build();

                        routeInfoList.add(routeInfo);
                    }
                }
            }
            zipRoutes = ZipRoutes.builder()
                    .zip(zip)
                    .routes(routeInfoList)
                    .build();


        } catch (IOException e) {
            e.printStackTrace();
        }
        return zipRoutes;
    }

}
