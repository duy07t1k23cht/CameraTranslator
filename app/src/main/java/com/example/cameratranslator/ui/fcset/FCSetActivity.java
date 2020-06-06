package com.example.cameratranslator.ui.fcset;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.RecyclerView;

import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.example.cameratranslator.R;
import com.example.cameratranslator.base.BaseActivity;
import com.example.cameratranslator.callback.StringCallback;
import com.example.cameratranslator.database.fcset.FCSet;
import com.example.cameratranslator.navigation.Navigation;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;

import static com.example.cameratranslator.utils.ViewUtils.toast;

public class FCSetActivity extends BaseActivity<FCSetPresenter> implements FCSetContract.View {

    private Toolbar myToolbar;
    private RecyclerView rvFCSets;
    private FloatingActionButton fabNewSet;

    @Override
    protected int getLayoutID() {
        return R.layout.activity_fc_set;
    }

    @Override
    protected FCSetPresenter initPresenter() {
        return new FCSetPresenter();
    }

    @Override
    protected void onCreate() {
        initViewComponents();
    }

    @Override
    protected void onResume() {
        super.onResume();

        mPresenter.createInteractor(getApplication());
        mPresenter.getAllSet();
    }

    private void initViewComponents() {

        // Setup Toolbar
        myToolbar = findViewById(R.id.myToolbar);
        setSupportActionBar(myToolbar);
        myToolbar.setNavigationOnClickListener(v -> onBackPressed());

        // Setup RecyclerView
        rvFCSets = findViewById(R.id.rv_fc_sets);
        rvFCSets.hasFixedSize();

        // Setup FloatingActionButton
        fabNewSet = findViewById(R.id.fab_new_set);
        fabNewSet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDialogAddNewSet();
            }
        });
    }

    private void showDialogAddNewSet() {
        View dialogView = getLayoutInflater().inflate(R.layout.dialog_add_fc_set, null);
        EditText edtNewSetName = dialogView.findViewById(R.id.edt_new_set_name);

        new AlertDialog.Builder(this)
                .setView(dialogView)
                .setPositiveButton(R.string.done, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                        mPresenter.addNewFCSet(edtNewSetName.getText().toString().trim());
                    }
                })
                .setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                })
                .create()
                .show();
    }

    @Override
    public void showFCSetsData(List<FCSet> fcSets) {
        FCSetAdapter adapter = new FCSetAdapter(
                FCSetActivity.this,
                fcSets,
                fcSetName -> Navigation.toSetDetailActivity(FCSetActivity.this, fcSetName));
        rvFCSets.setAdapter(adapter);
    }

    @Override
    public void displayMessage(String message) {
        toast(this, message);
    }

    @Override
    public void displayMessage(int stringResID) {
        toast(this, stringResID);
    }
}