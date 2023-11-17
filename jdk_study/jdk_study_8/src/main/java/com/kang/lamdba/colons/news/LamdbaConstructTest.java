package com.kang.lamdba.colons.news;

/**
 * User:
 * Description:
 * Date: 2022-09-10
 * Time: 16:47
 */
public class LamdbaConstructTest {

    public static void main(String[] args) {
        //传统写法
        String traditionName = "tradition";
        BookStoreInterface bookStoreInterface = new BookStoreInterface(){
            @Override
            public BookDTO saleBook(String bookName) {
                return new BookDTO(bookName);
            }
        };
        BookDTO bookDTO = bookStoreInterface.saleBook(traditionName);
        System.out.println(bookDTO);

        //lamdba写法
        String lambaName = "lamdba";
        BookStoreInterface bookStoreInterface2 = name -> new BookDTO(name);
        BookDTO lambaNameBook = bookStoreInterface2.saleBook(lambaName);
        System.out.println(lambaNameBook);

        //利用构造方法引用，简化lamdba
        String constructionName = "construction";
        BookStoreInterface bookStoreInterface3  = BookDTO::new;
        BookDTO constructionNameBook = bookStoreInterface3.saleBook(constructionName);
        System.out.println(constructionNameBook);

    }


}
