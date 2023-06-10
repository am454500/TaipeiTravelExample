/*
 *
 *  Created by paulz on 2023/6/8 下午4:07
 *  Last modified 2023/6/8 下午4:07
 */

@file:OptIn(ExperimentalFoundationApi::class)

package com.example.taipeitravel.uiAttractionDetail.composeUI


import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.PageSize
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.text.ClickableText
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.example.taipeitravel.R
import com.example.taipeitravel.model.Attraction.Attraction
import com.example.taipeitravel.model.Attraction.AttractionsResponse
import com.example.taipeitravel.model.Attraction.Image
import com.example.taipeitravel.util.BrowserHelper
import com.google.accompanist.pager.HorizontalPagerIndicator
import com.squareup.moshi.Moshi

@Composable
fun AttractionDetailCompose(attraction: Attraction) {
    val context = LocalContext.current
    Column(
        modifier = Modifier.fillMaxSize()
    ) {
        HorizontalPagerWithIndicators(images = attraction.images)
        LazyColumn(
            modifier = Modifier.weight(1f)
        ) {
            item {
                Text(
                    text = attraction.introduction,
                    style = MaterialTheme.typography.body1,
                    modifier = Modifier.padding(16.dp)
                )
            }
        }
        val text = buildAnnotatedString {
            pushStringAnnotation(tag = "URL", annotation = attraction.url)
            withStyle(style = SpanStyle(color = Color.Blue, fontWeight = FontWeight.Bold)) {
                append(attraction.url)
            }
            pop()
        }
        ClickableText(
            modifier = Modifier.padding(16.dp),
            text = text,
            onClick = { offset ->
            text.getStringAnnotations("URL", start = offset, end = offset)
                .firstOrNull()?.let {
                    BrowserHelper.startBrowser(context,attraction.url)
                }
        })
        Spacer(modifier = Modifier.height(20.dp))
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun HorizontalPagerWithIndicators(images: List<Image>) {
    val pagerState = rememberPagerState(
        initialPage = 0,
        initialPageOffsetFraction = 0f
    ) {
        images.size
        //pageCount
    }
    if (images.isNotEmpty()) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(200.dp)
        ) {
            HorizontalPager(
                modifier = Modifier,
                state = pagerState,
                pageSpacing = 0.dp,
                userScrollEnabled = true,
                reverseLayout = false,
                contentPadding = PaddingValues(0.dp),
                beyondBoundsPageCount = 0,
                pageSize = PageSize.Fill,
                key = null,
                pageContent = {
                    AsyncImage(
                        model = images[it].src,
                        contentDescription = null,
                        modifier = Modifier
                            .fillMaxWidth()
                            .fillMaxHeight(),
                        placeholder = painterResource(id = R.drawable.img_empty_img),
                        contentScale = ContentScale.Crop
                    )
                }
            )
            HorizontalPagerIndicator(
                modifier = Modifier
                    .align(Alignment.BottomCenter)
                    .padding(bottom = 10.dp),
                pageCount = images.size,
                pagerState = pagerState,
                activeColor = Color.White
            )
        }
    }
}

@Preview
@Composable
fun PlaceDetailsScreenPreview() {
    val attraction = Moshi.Builder().build()
        .adapter(AttractionsResponse::class.java)
        .fromJson(attractionExampleStr)
    AttractionDetailCompose(
        attraction = attraction!!.data[0]
    )
}

/**
 * 測試Attraction用字串
 */
private val attractionExampleStr = "{\n" +
        "  \"total\": 43,\n" +
        "  \"data\": [\n" +
        "    {\n" +
        "      \"id\": 86,\n" +
        "      \"name\": \"臺北市孔廟\",\n" +
        "      \"name_zh\": null,\n" +
        "      \"open_status\": 1,\n" +
        "      \"introduction\": \"臺北市孔廟於1927年興工，座落於文風丕盛的大龍峒老街裡，在臺北市政府規劃推動的都市更新下，與大龍國小、保安宮形成「廟、學、宮」的大龍峒文化園區，具有典儀場所、儒學象徵與市定古蹟等多重意涵。\\r\\n▲圖片來源：臺北市政府觀光傳播局\\r\\n\\r\\n臺北市孔廟採曲阜本廟建築形式，樑柱門窗皆未刻字顯得樸實又莊嚴，其布局依序為萬仞宮牆、泮池、櫺星門、儀門、大成殿及崇聖祠，另外可發現孔廟裡所有的柱子、門窗和其他寺廟不同，上面都沒有聯對，據說這表示沒人敢在至聖先師孔夫子門前賣弄文章；且因為民間興築，除形制與規格外，亦加入了區域性裝飾風格，即閩南地區特有之交趾陶與剪粘裝飾，尤以交趾陶裝飾為臺灣地區孔廟建築裡較特殊的一例。\\r\\n▲圖片來源：臺北市政府觀光傳播局\\r\\n\\r\\n孔廟的主殿為大成殿，正中央神龕奉祀至聖先師孔子牌位，上方高懸「有教無類」之黑底金字匾額，左右牆奉祀四配(復聖顏子、宗聖曾子、述聖子思、亞聖孟子)與12哲等牌位，瀰漫著莊嚴肅穆的氣息。孔廟的氣氛予人肅敬莊嚴之美，這也是中國儒家文化行事剛正之精神。\\r\\n▲圖片來源：臺北市政府觀光傳播局\\r\\n\\r\\n另大成殿裡豐富的交趾陶水車堵飾，是所有臺灣孔廟建築中獨樹一格的特色，值得細細觀賞建築之美。\\r\\n▲圖片來源：臺北市政府觀光傳播局\\r\\n\\r\\n孔廟雖然沒有眾多的神像與祭具，但卻擁有不少遵照古制所作的禮器與樂器，每逢9月28日釋奠典禮時表演音樂、舞蹈，並且呈獻牲、酒等祭品，以表達對於孔子的崇敬，每年都吸引了眾多國內外參觀人潮，也讓大家體驗中國傳統祭孔典禮之莊嚴氣氛。\\r\\n▲圖片來源：臺北市政府觀光傳播局\\r\\n\\r\\n近年來，孔廟積極活化古蹟，建置了文物展示室、多媒體展示室等設施，可具體瞭解孔廟之美及各項釋奠典禮的禮器、樂器等，還設置了新奇的八佾樂器展示中心，在圖說上輕輕一按，就可以聽到這些樂器的多元聲音，有些低沉，有些清脆高昂，讓遊人在平時就可體會八佾之音，甚至建置全臺孔廟唯一的華語、英語、日語、韓語導覽系統，以服務各國遊客，不僅如此，孔廟更常辦理各項結合傳統與現代藝術的創新活動，每年舉辦的大龍峒文化季系列活動，更可體驗祭禮文化與增進儒學的瞭解，更帶來參觀孔廟的新一波人潮。\\r\\n▲圖片來源：臺北市政府觀光傳播局\\r\\n\\r\\n除此之外，在孔廟的明倫堂裡另設有4D虛擬實境劇院，將孔廟的歷史沿革與孔子的生平事蹟以高科技影像技術輔以身歷其境的特效體驗，讓觀眾瞭解孔子與孔廟的故事，除了增添趣味之外，更以嶄新的形式延續了孔廟所代表的教育意義。\",\n" +
        "      \"open_time\": \"週二至週日(含國定假日)08:30-21:00  公休日：周一\",\n" +
        "      \"zipcode\": \"103\",\n" +
        "      \"distric\": \"大同區\",\n" +
        "      \"address\": \"103 臺北市大同區大龍街275號\",\n" +
        "      \"tel\": \"+886-2-25923934\",\n" +
        "      \"fax\": \"+886-2-25852730\",\n" +
        "      \"email\": \"ct@mail.taipei.gov.tw\",\n" +
        "      \"months\": \"01,07,02,08,03,09,04,10,05,11,06,12\",\n" +
        "      \"nlat\": 25.07295,\n" +
        "      \"elong\": 121.5166,\n" +
        "      \"official_site\": \"https://www.tctcc.taipei/\",\n" +
        "      \"facebook\": \"https://www.facebook.com/taipeiconfuciustemple\",\n" +
        "      \"ticket\": \"免費入園參觀\",\n" +
        "      \"remind\": \"每周一公休\\r\\n導覽服務預約相關資訊請參考官方網站。\",\n" +
        "      \"staytime\": \"\",\n" +
        "      \"modified\": \"2023-05-24 13:52:31 +08:00\",\n" +
        "      \"url\": \"https://www.travel.taipei/zh-tw/attraction/details/86\",\n" +
        "      \"category\": [\n" +
        "        {\n" +
        "          \"id\": 13,\n" +
        "          \"name\": \"歷史建築\"\n" +
        "        },\n" +
        "        {\n" +
        "          \"id\": 14,\n" +
        "          \"name\": \"宗教信仰\"\n" +
        "        },\n" +
        "        {\n" +
        "          \"id\": 19,\n" +
        "          \"name\": \"親子共遊\"\n" +
        "        }\n" +
        "      ],\n" +
        "      \"target\": [\n" +
        "        {\n" +
        "          \"id\": 61,\n" +
        "          \"name\": \"親子共學\"\n" +
        "        },\n" +
        "        {\n" +
        "          \"id\": 62,\n" +
        "          \"name\": \"校外教學\"\n" +
        "        }\n" +
        "      ],\n" +
        "      \"service\": [\n" +
        "        {\n" +
        "          \"id\": 146,\n" +
        "          \"name\": \"公廁\"\n" +
        "        }\n" +
        "      ],\n" +
        "      \"friendly\": [],\n" +
        "      \"images\": [\n" +
        "        {\n" +
        "          \"src\": \"https://www.travel.taipei/image/292831\",\n" +
        "          \"subject\": \"\",\n" +
        "          \"ext\": \".jpg\"\n" +
        "        },\n" +
        "        {\n" +
        "          \"src\": \"https://www.travel.taipei/image/292832\",\n" +
        "          \"subject\": \"\",\n" +
        "          \"ext\": \".jpg\"\n" +
        "        },\n" +
        "        {\n" +
        "          \"src\": \"https://www.travel.taipei/image/292833\",\n" +
        "          \"subject\": \"\",\n" +
        "          \"ext\": \".jpg\"\n" +
        "        },\n" +
        "        {\n" +
        "          \"src\": \"https://www.travel.taipei/image/292834\",\n" +
        "          \"subject\": \"\",\n" +
        "          \"ext\": \".jpg\"\n" +
        "        },\n" +
        "        {\n" +
        "          \"src\": \"https://www.travel.taipei/image/292835\",\n" +
        "          \"subject\": \"\",\n" +
        "          \"ext\": \".jpg\"\n" +
        "        },\n" +
        "        {\n" +
        "          \"src\": \"https://www.travel.taipei/image/292836\",\n" +
        "          \"subject\": \"\",\n" +
        "          \"ext\": \".jpg\"\n" +
        "        },\n" +
        "        {\n" +
        "          \"src\": \"https://www.travel.taipei/image/292837\",\n" +
        "          \"subject\": \"\",\n" +
        "          \"ext\": \".jpg\"\n" +
        "        },\n" +
        "        {\n" +
        "          \"src\": \"https://www.travel.taipei/image/292838\",\n" +
        "          \"subject\": \"\",\n" +
        "          \"ext\": \".jpg\"\n" +
        "        },\n" +
        "        {\n" +
        "          \"src\": \"https://www.travel.taipei/image/292839\",\n" +
        "          \"subject\": \"\",\n" +
        "          \"ext\": \".jpg\"\n" +
        "        },\n" +
        "        {\n" +
        "          \"src\": \"https://www.travel.taipei/image/292840\",\n" +
        "          \"subject\": \"\",\n" +
        "          \"ext\": \".jpg\"\n" +
        "        },\n" +
        "        {\n" +
        "          \"src\": \"https://www.travel.taipei/image/292830\",\n" +
        "          \"subject\": \"\",\n" +
        "          \"ext\": \".jpg\"\n" +
        "        }\n" +
        "      ],\n" +
        "      \"files\": [],\n" +
        "      \"links\": []\n" +
        "    }]}"
