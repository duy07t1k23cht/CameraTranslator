package com.example.cameratranslator.ui.object;

import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.RecyclerView;

import com.example.cameratranslator.R;
import com.example.cameratranslator.base.BaseActivity;
import com.example.cameratranslator.callback.ItemClickCallback;
import com.example.cameratranslator.customview.LabelImageView;
import com.example.cameratranslator.database.fcset.FCSet;
import com.example.cameratranslator.model.LocalizedObjectAnnotation;
import com.example.cameratranslator.navigation.Navigation;
import com.google.android.material.bottomsheet.BottomSheetBehavior;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

import static com.example.cameratranslator.utils.ViewUtils.*;


public class ObjectDetectionActivity extends BaseActivity<ObjectDetectionPresenter> implements ObjectDetectionContract.View {

    private LabelImageView imageView;
    private ImageView ivSpeak, ivSetting, ivAddFlashCard;
    private Toolbar toolbar;
    private FrameLayout layoutLoading, btnExpand;
    private LinearLayout bottomSheet;
    private TextView tvWord, tvLanguage;
    private RecyclerView rvObjects;
    private LinearLayout layoutSpeak;
    private ProgressBar pbLoadingAudio;

    private BottomSheetBehavior bottomSheetBehavior;

    private ItemClickCallback itemClickCallback;

    @Override
    protected int getLayoutID() {
        return R.layout.activity_object_detection;
    }

    @Override
    protected ObjectDetectionPresenter initPresenter() {
        return new ObjectDetectionPresenter();
    }

    @Override
    protected void onCreate() {

        mPresenter.createInteractor(getApplication());
        mPresenter.initDatas(getApplicationContext(), getIntent());

        itemClickCallback = position -> {
            mPresenter.setPosition(position);
        };

        initViewComponents();
    }

    @Override
    protected void onResume() {
        super.onResume();

        mPresenter.getPreference(getApplicationContext());
        mPresenter.getCurrentLanguage();
        mPresenter.setImageFromFile();

    }

    private void initViewComponents() {
        // Setup Toolbar
        toolbar = findViewById(R.id.myToolbar);
        setSupportActionBar(toolbar);
        toolbar.setNavigationOnClickListener(v -> onBackPressed());

        // Setup loading
        layoutLoading = findViewById(R.id.layout_loading);

        // Setup ImageView
        imageView = findViewById(R.id.image_view);
        imageView.setItemClickCallback(itemClickCallback);

        // Setup Bottom sheet
        bottomSheet = findViewById(R.id.layout_bottom_sheet);
        bottomSheetBehavior = BottomSheetBehavior.from(bottomSheet);
        bottomSheetBehavior.setBottomSheetCallback(new BottomSheetBehavior.BottomSheetCallback() {
            @Override
            public void onStateChanged(@NonNull View view, int i) {

            }

            @Override
            public void onSlide(@NonNull View view, float v) {
                // Animation for the arrow of the bottom sheet
                findViewById(R.id.iv_expand).setRotation(v * 180);
            }
        });
        collapseBottomSheet();

        btnExpand = findViewById(R.id.btn_expand);
        btnExpand.setOnClickListener(v -> {
            if (bottomSheetBehavior.getState() == BottomSheetBehavior.STATE_COLLAPSED) {
                expandBottomSheet();
            } else {
                collapseBottomSheet();
            }
        });

        // Setup TextView Data
        tvWord = findViewById(R.id.tv_word);

        // Setup text to speech
        tvLanguage = findViewById(R.id.tv_language);
        pbLoadingAudio = findViewById(R.id.progress_loading_audio);
        ivSpeak = findViewById(R.id.iv_speak);
        ivAddFlashCard = findViewById(R.id.iv_add_flash_card);
        ivAddFlashCard.setOnClickListener(v -> mPresenter.getAllSet()
//                mPresenter.toAddFlashCard(ObjectDetectionActivity.this)
        );
        ivSetting = findViewById(R.id.iv_setting);
        ivSetting.setOnClickListener(v -> Navigation.toSettingActivity(ObjectDetectionActivity.this));
        layoutSpeak = findViewById(R.id.layout_speak);
        layoutSpeak.setOnClickListener(v -> mPresenter.speakText());

        // Setup list object recyclerview
        rvObjects = findViewById(R.id.rv_objects);
        rvObjects.hasFixedSize();
    }

    @Override
    public void showLoading() {
        show(layoutLoading);
    }

    @Override
    public void dismissLoading() {
        dismiss(layoutLoading);
    }

    @Override
    public void clearOldData() {
        imageView.clear();
        collapseBottomSheet();
    }

    @Override
    public void collapseBottomSheet() {
        bottomSheetBehavior.setState(BottomSheetBehavior.STATE_COLLAPSED);
    }

    @Override
    public void expandBottomSheet() {
        bottomSheetBehavior.setState(BottomSheetBehavior.STATE_EXPANDED);
    }

    @Override
    public void setImage(Bitmap bitmap) {
        imageView.setImageBitmap(bitmap);
    }

    @Override
    public void localizeObject(List<LocalizedObjectAnnotation> localizedObjectAnnotations) {
        imageView.draw(localizedObjectAnnotations);
    }

    @Override
    public void setListObjectForBottomSheet(List<LocalizedObjectAnnotation> localizedObjectAnnotations) {
        ItemObjectAdapter itemObjectAdapter = new ItemObjectAdapter(
                this,
                localizedObjectAnnotations,
                itemClickCallback);
        rvObjects.setAdapter(itemObjectAdapter);

        if (!localizedObjectAnnotations.isEmpty())
            mPresenter.setPosition(0);
    }

    @Override
    public void setImageIndex(int position) {
        if (imageView != null) {
            imageView.setSelectedIndex(position);
        }
    }

    @Override
    public void setRecyclerViewIndex(int position) {
        if (rvObjects != null) {
            expandBottomSheet();

            // Highlight the card with the right object
            ItemObjectAdapter adapter = (ItemObjectAdapter) rvObjects.getAdapter();
            if (adapter != null)
                adapter.setSelectPosition(position);

            // Bring the card to visible area
            rvObjects.scrollToPosition(position);
        }
    }

    @Override
    public void showLayoutSpeakLoading() {
        show(pbLoadingAudio);
        dismiss(ivSpeak);
        layoutLoading.setClickable(false);
    }

    @Override
    public void showLayoutIsSpeaking() {
        dismiss(pbLoadingAudio);
        show(ivSpeak);
        ivSpeak.setImageDrawable(getDrawable(R.drawable.ic_stop));
        layoutLoading.setClickable(false);
    }

    @Override
    public void showDialogAllSet(List<FCSet> fcSetList) {
        AtomicInteger selectPosition = new AtomicInteger();

        ArrayList<String> allSetNames = new ArrayList<>();
        for (FCSet fcSet : fcSetList) {
            allSetNames.add(fcSet.getName());
        }

        new AlertDialog.Builder(this)
                .setTitle(R.string.select_a_set)
                .setSingleChoiceItems(
                        new ArrayAdapter<>(
                                getApplicationContext(),
                                android.R.layout.simple_list_item_single_choice,
                                allSetNames
                        ),
                        selectPosition.get(),
                        (dialog, position) -> {
                            selectPosition.set(position);
                        }
                )
                .setPositiveButton(R.string.done, (dialog, which) -> {
                    mPresenter.addToExistSet(allSetNames.get(selectPosition.get()));
                    dialog.dismiss();
                })
                .setNegativeButton(R.string.cancel, (dialog, which) -> dialog.dismiss())
                .create()
                .show();
    }

    @Override
    public void refreshLayoutSpeak() {
        dismiss(pbLoadingAudio);
        show(ivSpeak);
        ivSpeak.setImageDrawable(getDrawable(R.drawable.ic_speak));
        layoutLoading.setClickable(true);
    }

    @Override
    public void setCurrentLanguage(String language) {
        tvLanguage.setText(language);
    }

    @Override
    public void setObjectData(LocalizedObjectAnnotation localizedObjectAnnotation) {
        tvWord.setText(localizedObjectAnnotation.getTranslation());
    }

    @Override
    public void displayError(String error) {
        toast(this, error);
    }

    @Override
    public void displayError(int stringResID) {
        toast(this, stringResID);
    }
}
