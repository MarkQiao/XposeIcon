package com.markqiao.xposed;

import android.content.ComponentName;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.CompoundButton;
import android.widget.Switch;
import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity {
    @BindView(R.id.switch1)
    Switch aSwitch;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        aSwitch.setText(isIconEnabled()?"显示图标":"隐藏图标");
        aSwitch.setChecked(isIconEnabled());
        aSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (isIconEnabled()) {
                    aSwitch.setText("隐藏图标");
                } else {
                    aSwitch.setText("显示图标");
                }
                toggleLauncherIcon(!isIconEnabled());
            }
        });
    }
    //判断图标是否隐藏
    private boolean isIconEnabled() {
        return this.getPackageManager().getComponentEnabledSetting(getIconComponentName()) ==
                PackageManager.COMPONENT_ENABLED_STATE_ENABLED;
    }
    //控制隐藏图标
    private void toggleLauncherIcon(boolean newValue) {
        PackageManager packageManager = this.getPackageManager();
        int state = newValue ? PackageManager.COMPONENT_ENABLED_STATE_ENABLED : PackageManager.COMPONENT_ENABLED_STATE_DISABLED;
        packageManager.setComponentEnabledSetting(getIconComponentName(), state, PackageManager.DONT_KILL_APP);
    }
    //设置别名
    private ComponentName getIconComponentName() {
        return new ComponentName(this, "com.markqiao.xposed.MainActivityAlias");
    }
}
