package at.favre.lib.planb.full.util;

import android.graphics.drawable.Drawable;
import android.support.annotation.ColorInt;
import android.support.annotation.IdRes;
import android.support.v4.graphics.drawable.DrawableCompat;
import android.view.Menu;
import android.view.MenuItem;


public class ViewUtil {
    public static void tintMenuItem(Menu menu, @IdRes int iconId, @ColorInt int color) {
        MenuItem item = menu.findItem(iconId);
        Drawable icon = DrawableCompat.wrap(item.getIcon());
        DrawableCompat.setTint(icon, color);
        item.setIcon(icon);
    }
}
