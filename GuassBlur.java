package com.asknone.blur;

import android.content.Context;
import android.graphics.Bitmap;
import android.renderscript.Allocation;
import android.renderscript.Element;
import android.renderscript.RenderScript;
import android.renderscript.ScriptIntrinsicBlur;

public class GuassBlur implements BlurListener {
    public Context a;

    public GuassBlur(Context var1) {
        this.a = var1;
    }

    public Bitmap fastBlur(Bitmap var1) {
        RenderScript var13;
        Bitmap var10000;
        boolean var10001;
        try {
            var10000 = var1;
            var13 = RenderScript.create(this.a);
        } catch (Exception var11) {
            var10001 = false;
            return var1;
        }

        RenderScript var10002 = var13;
        RenderScript var12;
        RenderScript var10003 = var12 = var13;

        Allocation var16;
        try {
            var16 = Allocation.createFromBitmap(var10003, var1);
        } catch (Exception var10) {
            var10001 = false;
            return var1;
        }

        Allocation var2 = var16;

        ScriptIntrinsicBlur var14;
        try {
            var14 = ScriptIntrinsicBlur.create(var13, Element.U8_4(var10002));
        } catch (Exception var9) {
            var10001 = false;
            return var1;
        }

        ScriptIntrinsicBlur var3 = var14;

        try {
            var3.setRadius(25.0F);
            var14.setInput(var2);
            var10000 = Bitmap.createBitmap(var10000.getWidth(), var1.getHeight(), Bitmap.Config.ARGB_8888);
        } catch (Exception var8) {
            var10001 = false;
            return var1;
        }

        Bitmap var4 = var10000;

        Allocation var15;
        try {
            var14 = var3;
            var15 = Allocation.createFromBitmap(var12, var4);
        } catch (Exception var7) {
            var10001 = false;
            return var1;
        }

        Allocation var5 = var15;

        try {
            var3.forEach(var5);
            var5.copyTo(var4);
            var12.destroy();
            var2.destroy();
            var15.destroy();
            var14.destroy();
            return var10000;
        } catch (Exception var6) {
            var10001 = false;
            return var1;
        }
    }
}
