# 스프링부트로 크롤링 연습

## 특정사이트 크롤링 허용 확인
```
/robots.txt
ex) https://www.goodreads.com/robots.txt
```

## JSoup 라이브러리 추가 (gradle)
```
implementation group: 'org.jsoup', name: 'jsoup', version: '1.17.2'
```

## 크롤링 할 url 커넥트 문법
```
Document document = Jsoup.connect(url).get();
```

## 특정 엘리먼트 파싱방법 예제
```
ex)
for(Element el : document.select("p")) {
	sb.append(el.text()).append("\n");
}
```