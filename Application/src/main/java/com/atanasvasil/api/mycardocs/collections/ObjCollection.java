package com.atanasvasil.api.mycardocs.collections;

import com.atanasvasil.api.mycardocs.utils.*;
import java.util.ArrayList;
import java.util.List;
import lombok.ToString;

/**
 *
 * @author Atanas Yordanov Arshinkov
 * @since 2.0.0
 */
@ToString
public abstract class ObjCollection<T> {

    private Page page;
    private List<T> data = new ArrayList<>();

    public Page getPage() {
        return page;
    }

    public void setPage(Page page) {
        this.page = page;
    }

    public List<T> getData() {
        return data;
    }

    public void setData(List<T> data) {
        this.data = data;
    }
}
