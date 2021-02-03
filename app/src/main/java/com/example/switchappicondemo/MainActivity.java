package com.example.switchappicondemo;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ComponentName;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

/**
 * 修改App的图标以及App名字
 *
 * 1.修改Manifest  在加上下面的代码即可
 *  注意事项   使用的是自己的包名+<activity-alias   android:name=".icon_tag" 的名字
 *              String icon_tag = pkName+".icon_tag";
 *             String icon_tag_1212 = pkName+".icon_tag_1212";
 */
public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        TextView tvNormal = (TextView) findViewById(R.id.tv_normal);
        TextView tvFestival = (TextView) findViewById(R.id.tv_other);

        tvFestival.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(MainActivity.this, "为活动图标", Toast.LENGTH_SHORT).show();
                switchIcon(1);
            }

        });
        tvNormal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                switchIcon(2);
                Toast.makeText(MainActivity.this, "为用普通图标", Toast.LENGTH_SHORT).show();
            }
        });
    }

    /**
     * @param useCode 1、为活动图标 2 为用普通图标 3、不启用判断
     */

    String pkName="com.example.switchappicondemo";
    private void switchIcon(int useCode) {
        try {
            //要跟manifest的activity-alias 的name保持一致
            String icon_tag = pkName+".icon_tag";
            String icon_tag_1212 = pkName+".icon_tag_1212";
            if (useCode != 3) {
                PackageManager pm = getPackageManager();
                ComponentName normalComponentName = new ComponentName(getBaseContext(),
                        icon_tag);
                //正常图标新状态，此处使用用来修改清单文件中activity-alias下的android:enable的值
                int normalNewState = useCode == 2 ? PackageManager.COMPONENT_ENABLED_STATE_ENABLED
                        : PackageManager.COMPONENT_ENABLED_STATE_DISABLED;
                //新状态跟当前状态不一样才执行
                if (pm.getComponentEnabledSetting(normalComponentName) != normalNewState) {
                    //PackageManager.DONT_KILL_APP表示执行此方法时不杀死当前的APP进程
                    pm.setComponentEnabledSetting(
                            normalComponentName,
                            normalNewState,
                            PackageManager.DONT_KILL_APP);
                }

                ComponentName actComponentName = new ComponentName(
                        getBaseContext(),
                        icon_tag_1212);
                //活动图标新状态
                int actNewState = useCode == 1 ? PackageManager.COMPONENT_ENABLED_STATE_ENABLED
                        : PackageManager.COMPONENT_ENABLED_STATE_DISABLED;
                //新状态跟当前状态不一样才执行
                if (pm.getComponentEnabledSetting(actComponentName) != actNewState) {
                    pm.setComponentEnabledSetting(
                            actComponentName,
                            actNewState,
                            PackageManager.DONT_KILL_APP);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}