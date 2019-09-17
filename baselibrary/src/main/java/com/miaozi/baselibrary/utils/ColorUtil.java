package com.miaozi.baselibrary.utils;

import androidx.annotation.ColorInt;
import androidx.annotation.FloatRange;

/**
 * created by panshimu
 * on 2019/9/17
 */
public class ColorUtil {
    /**
     * Get ARGB color range in [primitiveColor, destColor]
     *
     * @param fraction       range[0, 1]
     * @param primitiveColor color associated with primitive changed
     * @param destColor      color associated with dest changed
     */
    public static int gradualChanged(float fraction, @ColorInt int primitiveColor, @ColorInt int destColor) {
        int startInt = (Integer) primitiveColor;
        float startA = ((startInt >> 24) & 0xff) / 255.0f;
        float startR = ((startInt >> 16) & 0xff) / 255.0f;
        float startG = ((startInt >> 8) & 0xff) / 255.0f;
        float startB = (startInt & 0xff) / 255.0f;

        int endInt = (Integer) destColor;
        float endA = ((endInt >> 24) & 0xff) / 255.0f;
        float endR = ((endInt >> 16) & 0xff) / 255.0f;
        float endG = ((endInt >> 8) & 0xff) / 255.0f;
        float endB = (endInt & 0xff) / 255.0f;

        // convert from sRGB to linear
        startR = (float) Math.pow(startR, 2.2);
        startG = (float) Math.pow(startG, 2.2);
        startB = (float) Math.pow(startB, 2.2);

        endR = (float) Math.pow(endR, 2.2);
        endG = (float) Math.pow(endG, 2.2);
        endB = (float) Math.pow(endB, 2.2);

        // compute the interpolated color in linear space
        float a = startA + fraction * (endA - startA);
        float r = startR + fraction * (endR - startR);
        float g = startG + fraction * (endG - startG);
        float b = startB + fraction * (endB - startB);

        // convert back to sRGB in the [0..255] range
        a = a * 255.0f;
        r = (float) Math.pow(r, 1.0 / 2.2) * 255.0f;
        g = (float) Math.pow(g, 1.0 / 2.2) * 255.0f;
        b = (float) Math.pow(b, 1.0 / 2.2) * 255.0f;

        return Math.round(a) << 24 | Math.round(r) << 16 | Math.round(g) << 8 | Math.round(b);
    }

    /**
     * 颜色透明化
     *
     * @param baseColor     需要更改的颜色
     * @param alphaPercent: 0 代表全透明, 1 代表不透明
     */
    public static int alphaColor(int baseColor, @FloatRange(from = 0f, to = 1f) float alphaPercent) {
        if (alphaPercent > 1) {
            alphaPercent = 1;
        }
        if (alphaPercent < 0) {
            alphaPercent = 0;
        }
        int baseAlpha = (baseColor & 0xff000000) >>> 24;
        int alpha = (int) (baseAlpha * alphaPercent);
        return alpha << 24 | (baseColor & 0xffffff);
    }
}
