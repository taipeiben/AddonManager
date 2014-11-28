package com.browniebytes.wow.addonmanager.model;

import org.codehaus.jackson.annotate.JsonProperty;

import java.io.Serializable;
import java.util.List;

public class Addon implements Serializable {
    private static final long serialVersionUID = 1;

    @JsonProperty("UID")
    private Long id;

    @JsonProperty("UICATID")
    private Long catId;

    @JsonProperty("UIVersion")
    private String version;

    @JsonProperty("UIDate")
    private Long date;

    @JsonProperty("UIName")
    private String addonName;

    @JsonProperty("UIAuthorName")
    private String authorName;

    @JsonProperty("UIFileInfoURL")
    private String fileInfoURL;

    @JsonProperty("UIDownloadTotal")
    private Long downloadTotal;

    @JsonProperty("UIFavoriteTotal")
    private Long favoriteTotal;

    @JsonProperty("UICompatibility")
    private List<AddonCompatibility> compatibility;

    @JsonProperty("UIDir")
    private List<String> uiDir;

    @JsonProperty("UIIMG_Thumbs")
    private List<String> imgThumbs;

    @JsonProperty("UIIMGs")
    private List<String> imgs;

    @JsonProperty("UISiblings")
    private List<Long> siblings;

    @JsonProperty("UIDonationLink")
    private String donationLink;

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    public Long getId() {
        return id;
    }

    public Long getCatId() {
        return catId;
    }

    public String getVersion() {
        return version;
    }

    public Long getDate() {
        return date;
    }

    public String getAddonName() {
        return addonName;
    }

    public String getAuthorName() {
        return authorName;
    }

    public String getFileInfoURL() {
        return fileInfoURL;
    }

    public Long getDownloadTotal() {
        return downloadTotal;
    }

    public Long getFavoriteTotal() {
        return favoriteTotal;
    }

    public List<AddonCompatibility> getCompatibility() {
        return compatibility;
    }

    public List<String> getUiDir() {
        return uiDir;
    }

    public List<String> getImgThumbs() {
        return imgThumbs;
    }

    public List<String> getImgs() {
        return imgs;
    }

    public List<Long> getSiblings() {
        return siblings;
    }

    public String getDonationLink() {
        return donationLink;
    }

    public static class AddonCompatibility {
        @JsonProperty("version")
        private String version;

        @JsonProperty("name")
        private String name;
    }
}
