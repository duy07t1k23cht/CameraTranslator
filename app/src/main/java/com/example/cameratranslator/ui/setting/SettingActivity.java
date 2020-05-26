package com.example.cameratranslator.ui.setting;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.example.cameratranslator.R;
import com.example.cameratranslator.base.BaseActivity;
import com.example.cameratranslator.navigation.Navigation;
import com.example.cameratranslator.utils.LanguageUtils;

public class SettingActivity extends BaseActivity<SettingPresenter> implements SettingContract.View {

    private ListView lstLanguages;

    @Override
    protected int getLayoutID() {
        return R.layout.activity_setting;
    }

    @Override
    protected SettingPresenter initPresenter() {
        return new SettingPresenter();
    }

    @Override
    protected void onCreate() {
        mPresenter.getPreference(getApplicationContext());

        initViewComponents();
        setListLanguage();
    }

    private void initViewComponents() {
        // Setup Toolbars
        Toolbar toolbar = findViewById(R.id.myToolbar);
        setSupportActionBar(toolbar);
        toolbar.setNavigationOnClickListener(v -> onBackPressed());

        // Setup  ListView
        lstLanguages = findViewById(R.id.list_languages);
        lstLanguages.setOnItemClickListener((parent, view, position, id) -> {
            mPresenter.setApplicationLanguage(position);
            onBackPressed();
        });
    }

    @Override
    public void setListLanguage() {
        ArrayAdapter arrayAdapter = new ArrayAdapter<>(
                getApplicationContext(),
                android.R.layout.simple_list_item_1,
                LanguageUtils.languages);
        lstLanguages.setAdapter(arrayAdapter);
    }
}
