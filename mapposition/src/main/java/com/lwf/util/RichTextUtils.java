package com.lwf.util;

import android.graphics.Color;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by zhangyanjiao on 16/8/3.
 */
public class RichTextUtils {
    public static SpannableString changeTopicColor(List<String> topicList, String text) {
        Map<Integer, Integer> topicMap = new HashMap<>();
        for (int j = 0; j < topicList.size(); j++) {
            if (text.contains(topicList.get(j))) {
                topicMap.put(text.indexOf("#" + topicList.get(j)), text.indexOf("#" + topicList.get(j)) + topicList.get(j).length() + 1);
            }
        }

        SpannableString spannableString = new SpannableString(text);
        try {
            for (Integer key : topicMap.keySet()) {
                spannableString
                        .setSpan(new ForegroundColorSpan(Color.BLUE), key, topicMap.get(key) + 1, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
            }
        } catch (IndexOutOfBoundsException e) {
        }
        return spannableString;
    }
}
