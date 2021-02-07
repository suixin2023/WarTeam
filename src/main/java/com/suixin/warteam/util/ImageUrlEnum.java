package com.suixin.warteam.util;

/**
 * 图片资源枚举
 */
public enum ImageUrlEnum {
    BACKGROUND("BACKGROUND","mainbeijing.png", "通用背景"),
    CCK_BACKGROUND("CCK_BACKGROUND","cck/cckbeijing.png", "猜猜看背景"),
    LHD_BACKGROUND("LHD_BACKGROUND","lhd/lhdbeijing.png", "萌之争背景"),
    SOLO_BACKGROUND("SOLO_BACKGROUND","solo/main/solobeijing.png", "solo背景"),
    ;

    private String id;
    private String url;
    private String name;

    ImageUrlEnum(String id, String url, String name) {
        this.id = id;
        this.url = url;
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getUrl() {
        return "[local]tavern/"+url;
    }

    public static String getUrl(Integer img) {
        return "[local]tavern/cck/"+img+".png";
    }
}
