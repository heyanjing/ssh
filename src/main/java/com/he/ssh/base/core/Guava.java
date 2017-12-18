package com.he.ssh.base.core;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by heyanjing on 2017/12/18 16:41.
 */
public class Guava {

    public static <K, V> HashMap<K, V> newHashMap() {
        return new HashMap();
    }

    public static <E> ArrayList<E> newArrayList() {
        return new ArrayList();
    }
}
