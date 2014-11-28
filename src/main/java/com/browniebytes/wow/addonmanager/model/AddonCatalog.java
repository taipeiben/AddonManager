package com.browniebytes.wow.addonmanager.model;

import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.Multimap;

public class AddonCatalog {

    Multimap<String, Addon> subAddonToAddonMap;

    public void loadCatalog(final Addon[] addons) {
        subAddonToAddonMap = ArrayListMultimap.create();
        for (Addon addon : addons) {

        }
    }
}
