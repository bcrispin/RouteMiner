package com.brennan.finalproject.routeminer.clients;

import com.brennan.finalproject.routeminer.models.ZipInfo;
import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.*;

import java.io.IOException;
import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SearchBugClient {

    /**
     *
     * @param zip
     * @param radius
     * @return
     */
    public static List<String> getZips(int zip, int radius) {
        String baseUrl = "https://www.searchbug.com/tools/zip-radius.aspx" ;
        List<String> zips = new ArrayList<>();

        WebClient client = new WebClient();
        client.getOptions().setCssEnabled(false);
        client.getOptions().setJavaScriptEnabled(false);
        try {
            String searchUrl = baseUrl + String.format("?TYPE=zipradius/&ZIP=%d&DIST=%d&submit=Search", zip, radius);
            HtmlPage page = client.getPage(searchUrl);
            HtmlElement element = page.getAnchorByText(Integer.toString(zip));
            zips.add(((DomText)element.getFirstChild()).getData());
            DomNode parent = element.getParentNode().getParentNode();
            while (parent.getNextSibling() != null) {
                parent = parent.getNextSibling();
                DomNode node = parent.getFirstChild().getFirstChild();
                if (node != null) {
                    zips.add(((DomText)node.getFirstChild()).getData());
                }
            }
            System.out.println();
        } catch(Exception e){
            e.printStackTrace();
        }
        return zips;
    }

    /**
     *
     * @param zips
     * @return
     */
    public static List<ZipInfo> getAllZipInfo(List<String> zips) {
        List<ZipInfo> zipInfoList = new ArrayList<>();

        for (String zip : zips) {
            zipInfoList.add(SearchBugClient.getZipInfo(zip));
        }

        return zipInfoList;
    }

    /**
     *
     * @param zip
     * @return
     */
    public static ZipInfo getZipInfo(String zip) {
        ZipInfo zipInfo = null;

        String baseUrl = "https://www.searchbug.com/tools/zip-code-lookup.aspx";

        WebClient client = new WebClient();
        client.getOptions().setCssEnabled(false);
        client.getOptions().setJavaScriptEnabled(false);

        try {
            String searchUrl = baseUrl + "?TYPE=zip2city&ZIP=" + zip;
            Map<String, String> zipInfoMap = new HashMap<>();
            HtmlPage page = client.getPage(searchUrl);

            HtmlElement table = page.getFirstByXPath("//*[@id=\"rszipDIV\"]/p/table/tbody/tr/td[1]/table/tbody");
            DomNodeList<DomNode> rows = table.getChildNodes();
            for (DomNode row : rows) {
                List<HtmlElement> fontElements = row.getByXPath(".//font");
                if (fontElements.size() == 2) {
                    String key = fontElements.get(0).getFirstChild().getFirstChild().toString();
                    String value = fontElements.get(1).getFirstChild().toString();
                    zipInfoMap.put(key, value);
                }
            }
            zipInfo = ZipInfo.builder()
                    .zip(zip)
                    .location(zipInfoMap.getOrDefault("Location", ""))
                    .county(zipInfoMap.getOrDefault("County", ""))
                    .metroArea(zipInfoMap.getOrDefault("Metro Area", "0"))
                    .landArea(zipInfoMap.getOrDefault("Land Area", "0"))
                    .elevation(zipInfoMap.getOrDefault("Elevation", "0"))
                    .population(zipInfoMap.getOrDefault("Population", "0"))
                    .homeValue(zipInfoMap.getOrDefault("Average House Value", "0"))
                    .householdIncome(zipInfoMap.getOrDefault("Income Per Household", "0"))
                    .medianAge(zipInfoMap.getOrDefault("Median Age", "0"))
                    .businesses(zipInfoMap.getOrDefault("Number of Businesses", "0"))
                    .employees(zipInfoMap.getOrDefault("Number of Employees", "0"))
                    .build();

        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return zipInfo;
    }
}



