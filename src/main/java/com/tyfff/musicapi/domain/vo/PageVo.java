package com.tyfff.musicapi.domain.vo;

import com.baomidou.mybatisplus.core.metadata.IPage;
import lombok.Data;

import java.util.List;

@Data
public class PageVo<T> {
    private long total;         // 总记录数
    private long pageSize;      // 每页显示的记录数
    private long currentPage;   // 当前页码
    private List<T> records;    // 当前页的数据记录列表

    public void copyFromPage(IPage<T> page){
        this.total = page.getTotal();
        this.pageSize = page.getSize();
        this.records = page.getRecords();
        this.currentPage = page.getCurrent();
    }
    // 省略 getter 和 setter 方法
}
