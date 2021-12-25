package pl.kaczmarek.utils;

import static java.lang.Integer.min;

public class Pagination {

    public Integer page_number;
    public Integer on_page;

    public Pagination(Integer page_number, Integer on_page) {
        if  (page_number == null) {
            this.page_number = 1;
        }
        else {
            this.page_number = page_number;
        }
        if  (on_page == null) {
            this.on_page = 20;
        }
        else {
            if(on_page>30) {
                this.on_page = 30;
            }
            else {
                this.on_page = on_page;
            }
        }
    }

    private void initPageNumber() {
        this.page_number = 1;
    }
    private void initOnPage() {
        this.on_page = 30;
    }

    public Integer getStartIndex(Integer listSize) {
        if (this.page_number == null) {
            initPageNumber();
        }
        if (this.on_page == null) {
            initOnPage();
        }
        return min((this.page_number - 1) * this.on_page, listSize);
    }

    public  Integer getEndIndex(Integer listSize) {
        if (this.page_number == null) {
            initPageNumber();
        }
        if (this.on_page == null) {
            initOnPage();
        }
        return min((this.page_number - 1) * this.on_page + this.on_page, listSize);
    }
}
