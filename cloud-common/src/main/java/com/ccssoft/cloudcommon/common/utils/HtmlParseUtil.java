package com.ccssoft.cloudcommon.common.utils;



import com.ccssoft.cloudcommon.entity.Content;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Component;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;


/**
 * @author moriarty
 * @date 2020/7/7 16:11
 */
@Component
public class HtmlParseUtil {
    public List<Content> parseJD(String keywords) throws IOException {
        //获取请求
        String url = "https://search.jd.com/Search?keyword="+keywords;
        //解析网页，返回的document对象就是浏览器Document对象
        Document document = Jsoup.parse(new URL(url), 30000);

        Element element = document.getElementById("J_goodsList");

        //获取所有的li元素
        Elements elements = element.getElementsByTag("li");
        //获取其中的内容
        List<Content> list = new ArrayList();
        for (Element el : elements) {
            String img = el.getElementsByTag("img").eq(0).attr("src");
            String price = el.getElementsByClass("p-price").eq(0).text();
            String title = el.getElementsByClass("p-name").eq(0).text();
            //封装进对象就可以自行使用了
            Content content = new Content(title,img,price);
            list.add(content);
        }
        return list;
    }
}
