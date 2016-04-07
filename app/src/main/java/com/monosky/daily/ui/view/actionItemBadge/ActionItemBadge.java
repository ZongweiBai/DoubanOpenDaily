package com.monosky.daily.ui.view.actionItemBadge;

import android.app.Activity;
import android.graphics.Color;
import android.graphics.Point;
import android.graphics.drawable.Drawable;
import android.view.Display;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.monosky.daily.R;
import com.monosky.daily.ui.view.actionItemBadge.utils.BadgeStyle;
import com.monosky.daily.ui.view.actionItemBadge.utils.UIUtil;


/**
 * Created by mikepenz on 23.07.14.
 */
public class ActionItemBadge {
    public enum BadgeStyles {
        DEFAULT(new BadgeStyle(BadgeStyle.Style.DEFAULT, R.layout.menu_badge, Color.parseColor("#2196F3"), Color.parseColor("#2196F3"), Color.WHITE)),
        DARK_GREY(new BadgeStyle(BadgeStyle.Style.DEFAULT, R.layout.menu_badge, Color.parseColor("#606060"), Color.parseColor("#3e3e3e"), Color.WHITE)),
        RED(new BadgeStyle(BadgeStyle.Style.DEFAULT, R.layout.menu_badge, Color.parseColor("#FF4444"), Color.parseColor("#CC0000"), Color.WHITE)),
        BLUE(new BadgeStyle(BadgeStyle.Style.DEFAULT, R.layout.menu_badge, Color.parseColor("#33B5E5"), Color.parseColor("#0099CC"), Color.WHITE)),
        GREEN(new BadgeStyle(BadgeStyle.Style.DEFAULT, R.layout.menu_badge, Color.parseColor("#99CC00"), Color.parseColor("#669900"), Color.WHITE));

        private BadgeStyle style;

        BadgeStyles(BadgeStyle style) {
            this.style = style;
        }

        public BadgeStyle getStyle() {
            return style;
        }
    }

    public static void update(final Activity activity, final MenuItem menu, int resId, String badgeCount) {
        update(activity, menu, resId, null, String.valueOf(badgeCount));
    }

    public static void update(final Activity activity, final MenuItem menu, Drawable icon, String badgeCount) {
        update(activity, menu, icon, null, badgeCount);
    }

    /**
     * update the given menu item with icon, badgeCount and style
     *
     * @param activity
     * @param menu
     * @param resId
     * @param style
     * @param badgeCount
     */
    public static void update(final Activity activity, final MenuItem menu, int resId, BadgeStyle style, String badgeCount) {
        if(menu == null) {
            return;
        }
        if(resId <= 0) {
            return;
        }
        Drawable icon = activity.getResources().getDrawable(resId);
        update(activity, menu, icon, style, badgeCount);
    }

    /**
     * update the given menu item with icon, badgeCount and style
     *
     * @param activity   use to bind onOptionsItemSelected
     * @param menu
     * @param icon
     * @param style
     * @param badgeCount
     */
    public static void update(final Activity activity, final MenuItem menu, Drawable icon, BadgeStyle style, String badgeCount) {
        if (menu == null) return;

        RelativeLayout badge;
        TextView badgeView;
        ImageView imageView;

        if (style == null) {
            style = BadgeStyles.DEFAULT.getStyle();
        }
        badge = (RelativeLayout) menu.setActionView(style.getLayout()).getActionView();

        badgeView = (TextView) badge.findViewById(R.id.menu_badge);
        imageView = (ImageView) badge.findViewById(R.id.menu_badge_icon);

        //Display icon in ImageView
        if (imageView != null && icon != null) {
            imageView.setImageDrawable(icon);
        }

        //Bind onOptionsItemSelected to the activity
        if (activity != null) {
            badge.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    activity.onOptionsItemSelected(menu);
                }
            });

            badge.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    Display display = activity.getWindowManager().getDefaultDisplay();
                    Point size = new Point();
                    display.getSize(size);
                    int width = size.x;
                    Toast toast = Toast.makeText(activity, menu.getTitle(), Toast.LENGTH_SHORT);
                    toast.setGravity(Gravity.TOP, width / 5, UIUtil.convertDpToPx(activity, 48)); //your width and height
                    toast.show();
                    return true;
                }
            });
        }

        //Apply style if it's set
        if (style != null) {
            // 这句代码是设置badge被选中和展示的样式，不需要可注释掉
//            UIUtil.setBackground(badgeView, new BadgeDrawableBuilder().color(style.getColor()).colorPressed(style.getColorPressed()).build(activity));
            badgeView.setTextColor(style.getTextColor());
        }

        //Manage min value
        if (badgeCount == null) {
            badgeView.setVisibility(View.GONE);
        } else {
            badgeView.setVisibility(View.VISIBLE);
            badgeView.setText(badgeCount);
        }

        menu.setVisible(true);
    }


    /**
     * hide the given menu item
     *
     * @param menu
     */
    public static void hide(MenuItem menu) {
        menu.setVisible(false);
    }
}
