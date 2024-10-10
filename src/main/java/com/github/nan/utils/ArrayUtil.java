package com.github.nan.utils;

/**
 * @author NanNan Wang
 */
public class ArrayUtil {
    public static void reverse(int[] arr, int fromIndex, int toIndex) {
        int left = fromIndex;
        int right = toIndex;

        while (left < right) {
            int temp = arr[left];
            arr[left] = arr[right];
            arr[right] = temp;
            left++;
            right--;
        }
    }
}
