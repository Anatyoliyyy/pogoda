import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;


public class Parser {

    private static Document getPage() throws IOException{
        String url = "http://www.pogoda.spb.ru/";
        Document page = Jsoup.parse(new URL(url), 3000);
        return page;
    }

    //22.04 Суббота погода сегодня
    //22.04
    // \d{2}\.\d{2}
    private static Pattern pattern = Pattern.compile("\\d{2}\\.\\d{2}");

    private static String getDateFromString(String stringDate) throws Exception {
        Matcher matcher = pattern.matcher(stringDate);
        if (matcher.find()){
            return matcher.group();
        }
        throw new Exception("Can`t extract date from string!");

    }

    private static int printPartValues(Elements values, int index){
        int iterationCount = 4;
        if (index == 0){
            Element valueLn = values.get(3);
            boolean isMorning = valueLn.text().contains("Утро");
            if (isMorning){
                iterationCount = 3;
            }
        }

        for (int i = 0; i < iterationCount; i++) {
            Element valueLine = values.get(index + i);
            for (Element td : valueLine.select("td")){
                System.out.print(td.text() + "    ");
            }
            System.out.println();
        }
        return iterationCount;

    }

    public static void main(String[] args) throws Exception {
        Document page = getPage();
        // css query languege
        Element tableWth = page.select("table[class=wt]").first();
        Elements names = tableWth.select("tr[class=wth");
        Elements values = tableWth.select("tr[valign=top");
        int index = 0;
        for (Element name : names){
            String dateString = name.select("th[id=dt").text();
            String date = getDateFromString(dateString);
            System.out.println(date + "     Явления  Температура   Давл   Влажность   Ветер");
            int iterationCount = printPartValues(values, index);
            index = index + iterationCount;

        }

    }
}

//25.01     Явления  Температура   Давл   Влажность   Ветер
//День    Переменная облачность Без осадков.    -11..-13    765    90%    [Ю] 1-3 м/с
//Вечер    Облачно. Без осадков.    -14..-16    763    92%    [Ю] 1-3 м/с
//Ночь    Пасмурно. Без осадков.    -13..-15    761    90%    [Ю-В] 2-4 м/с
//26.01     Явления  Температура   Давл   Влажность   Ветер
//Утро    Пасмурно. Местами небольшой снег.    -10..-12    756    95%    [В] 3-5 м/с
//День    Пасмурно. Небольшой снег.    -9..-11    753    93%    [В] 3-5 м/с
//Вечер    Пасмурно. Небольшой снег.    -10..-12    751    97%    [Ю-В] 1-3 м/с
//Ночь    Пасмурно. Без существенных осадков.    -12..-14    750    98%    [Ю-В] 0-2 м/с
//27.01     Явления  Температура   Давл   Влажность   Ветер
//Утро    Пасмурно. Без осадков.    -13..-15    749    98%    [Ю-З] 0-2 м/с
//День    Облачно. Без осадков.    -12..-14    748    97%    [З] 0-2 м/с
//Вечер    Облачно. Без осадков.    -17..-19    749    97%    [З] 1-3 м/с
//Ночь    Облачно. Без осадков.    -16..-18    751    98%    [З] 1-3 м/с
//28.01     Явления  Температура   Давл   Влажность   Ветер
//Утро    Облачно. Без осадков.    -19..-21    754    94%    [З] 1-3 м/с
//День    Переменная облачность Без осадков.    -14..-16    757    90%    [Ю-В] 0-1 м/с
//Вечер    Облачно. Без осадков.    -16..-18    757    93%    [В] 1-3 м/с
//Ночь    Пасмурно. Без осадков.    -13..-15    755    90%    [В] 3-5 м/с
//29.01     Явления  Температура   Давл   Влажность   Ветер
//Утро    Пасмурно. Местами небольшой снег.    -11..-13    752    93%    [Ю-В] 3-5 м/с
//День    Пасмурно. Без осадков.    -8..-10    750    94%    [Ю-В] 2-4 м/с
//Вечер    Пасмурно. Небольшой снег, местами умеренный. Метель.    -6..-8    747    96%    [В] 2-4 м/с
//Ночь    Пасмурно. Небольшой дождь.    -1..+1    744    96%    [Ю] 2-4 м/с
//
//Process finished with exit code 0
