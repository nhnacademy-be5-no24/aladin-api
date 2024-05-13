package com.nhnacademy.shop.controller;

import com.nhnacademy.shop.auth.AuthService;
import com.nhnacademy.shop.book.entity.Book;
import com.nhnacademy.shop.book.repository.BookRepository;
import com.nhnacademy.shop.bookcategory.domain.BookCategory;
import com.nhnacademy.shop.bookcategory.repository.BookCategoryRepository;
import com.nhnacademy.shop.category.domain.Category;
import com.nhnacademy.shop.category.repository.CategoryRepository;
import com.nhnacademy.shop.object.FileService;
import com.nhnacademy.shop.object.ObjectService;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import javax.transaction.Transactional;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Scanner;

@RestController
public class BookSave {
    private final BookRepository bookRepository;
    private final CategoryRepository categoryRepository;
    private final BookCategoryRepository bookCategoryRepository;
    @Value("${aladin.url}")
    private String aladinUrl;
    @Value("${ttbkey}")
    private String key;

    @Value("${nhncloud.auth.url}")
    private String authUrl;
    @Value("${nhncloud.auth.tenant-id}")
    private String tenantId = "{Tenant ID}";
    @Value("${nhncloud.auth.username}")
    private String username = "{NHN Cloud Account}";
    @Value("${nhncloud.auth.password}")
    private String password = "{API Password}";
    @Value("${nhncloud.storage.url}")
    private String storageUrl;
    @Value("${nhncloud.storage.container}")
    private String containerName;

    private AuthService authService;
    private FileService fileService;
    private ObjectService objectService;
    private String token;
    private int currentBook = 0;

    public BookSave(BookRepository bookRepository, CategoryRepository categoryRepository, BookCategoryRepository bookCategoryRepository) {
        this.bookRepository = bookRepository;
        this.categoryRepository = categoryRepository;
        this.bookCategoryRepository = bookCategoryRepository;
    }

    @GetMapping("/")
    public String saveBooks() {
        int currentBook = 0;

        // api url 설정
        authService = new AuthService(authUrl, tenantId, username, password);
        fileService = new FileService();
        String token = new JSONObject(authService.requestToken())
                .getJSONObject("access")
                .getJSONObject("token")
                .getString("id");

        objectService = new ObjectService(storageUrl, token);

        RestTemplate restTemplate = new RestTemplate();
        String[] categoryIds;

        try {
            File file = new File("src/main/resources/static/categoryList.txt");
            Scanner scanner = new Scanner(file);
            String str = scanner.nextLine();

            categoryIds = str.split(" ");

            for (int i = 0; i < categoryIds.length; i++) {
                System.out.println(categoryIds[i]);
                String url = buildUrl(categoryIds[i]);
                HttpHeaders headers = new HttpHeaders();
                headers.setContentType(MediaType.APPLICATION_JSON_UTF8);
                String response = new String(restTemplate.getForObject(url, byte[].class), StandardCharsets.UTF_8);

                // item 배열 추출
                JSONArray itemArray = new JSONObject(response.substring(response.indexOf("{"))).getJSONArray("item");

                for(int j = 0; j < itemArray.length(); j++) {
                    JSONObject item = itemArray.getJSONObject(j);
                    saveBook(item);
                }
            }
        } catch (FileNotFoundException e) {
            // 파일을 찾을 수 없는 경우 예외 처리
            e.printStackTrace();
        }

        return "";
    }

    @Transactional
    public void saveBook(JSONObject item) {
        // 추출한 데이터 출력
//        System.out.println("Title: " + itemTitle);
//        System.out.println("Author: " + itemAuthor);
//        System.out.println("Publication Date: " + itemPubDate);
//        System.out.println("Description: " + itemDescription);
//        System.out.println("ISBN: " + itemISBN);
//        System.out.println("Sales Price: " + itemPriceSales); // 할인가
//        System.out.println("Standard Price: " + itemPriceStandard); // 정가
//        System.out.println("Cover: " + itemCover); // image link
//        System.out.println("Publisher: " + itemPublisher); // 출판사
//        System.out.println("Category Name: " + itemCategoryName); // 카테고리 이름

        try {
            // 책 데이터 수집
            String itemTitle = item.getString("title");
            System.out.println(itemTitle);
            String itemAuthor = item.getString("author");
            String[] itemPubDate = item.getString("pubDate").split("-");
            LocalDate publishedAt = LocalDate.of(Integer.parseInt(itemPubDate[0]),
                    Integer.parseInt(itemPubDate[1]),
                    Integer.parseInt(itemPubDate[2])
            );
            String itemDescription = item.getString("description");
            String itemISBN = item.getString("isbn");
            int itemPriceSales = item.getInt("priceSales");
            int itemPriceStandard = item.getInt("priceStandard");
            String itemCover = item.getString("cover");
            String itemCategoryName = item.getString("categoryName");
            String itemPublisher = item.getString("publisher");

            // file 데이터 수집
            String fileUrl = itemCover;
            String[] url = fileUrl.split("/");
            String objectName = itemISBN + "." + url[url.length - 1].split("\\.")[1];

            Book book = Book.builder()
                    .bookIsbn(itemISBN)
                    .bookTitle(itemTitle)
                    .bookDesc(itemDescription)
                    .bookPublisher(itemPublisher)
                    .bookPublishedAt(publishedAt)
                    .bookFixedPrice(itemPriceStandard)
                    .bookSalePrice(itemPriceSales)
                    .bookIsPacking(true)
                    .bookViews(0L)
                    .bookStatus(0)
                    .bookQuantity(2000)
                    .bookImage(objectName)
                    .author(itemAuthor)
                    .likes(0L)
                    .build();

            book = bookRepository.save(book);

            String[] categoryNames = itemCategoryName.split(">");
            List<Category> categories = new ArrayList<>();

            for(int i = 0; i < categoryNames.length; i++) {
                if(Objects.isNull(categoryRepository.findByCategoryName(categoryNames[i]))) {
                    Category category = Category.builder()
                            .categoryId(null)
                            .categoryName(categoryNames[i])
                            .parentCategory(null)
                            .build();
                    category = categoryRepository.save(category);
                    categories.add(category);
                }
                else {
                    Category category = categoryRepository.findByCategoryName(categoryNames[i]);
                    categories.add(category);
                }
            }

            // 최상위 노드 저장
            BookCategory bookCategory = BookCategory.builder()
                    .pk(new BookCategory.Pk(book.getBookIsbn(), categories.get(0).getCategoryId()))
                    .book(book)
                    .category(categories.get(0))
                    .build();

            bookCategoryRepository.save(bookCategory);

            for(int i = 1; i < categories.size(); i++) {
                Category category = categories.get(i);
                Category newCategory = Category.builder()
                        .categoryId(category.getCategoryId())
                        .categoryName(category.getCategoryName())
                        .parentCategory(categories.get(i - 1))
                        .build();

                newCategory = categoryRepository.save(newCategory);

                bookCategory = BookCategory.builder()
                        .pk(new BookCategory.Pk(book.getBookIsbn(), newCategory.getCategoryId()))
                        .book(book)
                        .category(newCategory)
                        .build();

                bookCategoryRepository.save(bookCategory);
            }

            // 파일 업로드
            InputStream inputStream = fileService.getFileByUrl(fileUrl);
            objectService.uploadObject(containerName, objectName, inputStream);
//
            System.out.println("\nUpload OK (" + ++currentBook + ")");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public String buildUrl(String categoryId) {
        return UriComponentsBuilder.fromHttpUrl(aladinUrl)
                .queryParam("ttbkey", key)
                .queryParam("Query", "book")
                .queryParam("QueryType", "Book")
                .queryParam("MaxResults", 20)
                .queryParam("Start", 1)
                .queryParam("SearchTarget", "Book")
                .queryParam("Output", "JS")
                .queryParam("categoryId", categoryId)
                .queryParam("Version", "20070901")
                .queryParam("Cover", "Big")
                .toUriString();
    }
}
