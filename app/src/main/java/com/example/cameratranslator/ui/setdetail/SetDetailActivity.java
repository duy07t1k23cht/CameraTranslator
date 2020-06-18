package com.example.cameratranslator.ui.setdetail;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.cameratranslator.R;
import com.example.cameratranslator.base.BaseActivity;
import com.example.cameratranslator.database.flashcard.FlashCard;
import com.example.cameratranslator.utils.BitmapUtils;
import com.example.cameratranslator.utils.LanguageUtils;
import com.wajahatkarim3.easyflipview.EasyFlipView;

import static com.example.cameratranslator.utils.ViewUtils.dismiss;
import static com.example.cameratranslator.utils.ViewUtils.hide;
import static com.example.cameratranslator.utils.ViewUtils.show;
import static com.example.cameratranslator.utils.ViewUtils.toast;

public class SetDetailActivity extends BaseActivity<SetDetailPresenter> implements SetDetailContract.View, View.OnClickListener {

    private ImageView ivFCImageBack, ivFCImageFront, ivSpeakBack, ivSpeakFront;
    private LinearLayout btnSpeakBack, btnSpeakFront;
    private ProgressBar pbLoadingAudioBack, pbLoadingAudioFront;
    private TextView tvFCWordBack, tvWordFront, btnIsLearnBack, getBtnIsLearnFront;
    private TextView tvNext, tvPrev, tvPage, tvLanguageFront, tvLangugeBack;
    private EasyFlipView flipView;
    private Toolbar toolbar;

    @Override
    protected int getLayoutID() {
        return R.layout.activity_set_detail;
    }

    @Override
    protected SetDetailPresenter initPresenter() {
        return new SetDetailPresenter();
    }

    @Override
    protected void onCreate() {
        initViewComponents();
        implementListeners();

        mPresenter.createInteractor(getApplication());
        mPresenter.getIntentData(getIntent());
        mPresenter.initSetFlashCard();
    }

    private void initViewComponents() {
        // Toolbar
        toolbar = findViewById(R.id.myToolbar);
        setSupportActionBar(toolbar);
        toolbar.setNavigationOnClickListener((v) -> onBackPressed());

        // FlipView
        flipView = findViewById(R.id.flipView);

        // LayoutBack
        ivFCImageBack = findViewById(R.id.iv_fc_image_back);
        btnSpeakBack = findViewById(R.id.layout_speak_back);
        ivSpeakBack = findViewById(R.id.iv_speak_back);
        tvLangugeBack = findViewById(R.id.tv_language_back);
        pbLoadingAudioBack = findViewById(R.id.progress_loading_audio_back);
        tvFCWordBack = findViewById(R.id.tv_word_back);
        btnIsLearnBack = findViewById(R.id.btn_is_learned_back);

        // Front
        ivFCImageFront = findViewById(R.id.iv_fc_image_front);
        btnSpeakFront = findViewById(R.id.layout_speak_front);
        ivSpeakFront = findViewById(R.id.iv_speak_front);
        tvLanguageFront = findViewById(R.id.tv_language_front);
        pbLoadingAudioFront = findViewById(R.id.progress_loading_audio_front);
        tvWordFront = findViewById(R.id.tv_word_front);
        getBtnIsLearnFront = findViewById(R.id.btn_is_learned_front);

        // LayoutAction
        tvNext = findViewById(R.id.tv_next);
        tvPrev = findViewById(R.id.tv_prev);
        tvPage = findViewById(R.id.tv_page);
    }

    private void implementListeners() {
        flipView.setOnClickListener(this);

        btnSpeakBack.setOnClickListener(this);
        btnSpeakFront.setOnClickListener(this);
        btnIsLearnBack.setOnClickListener(this);
        getBtnIsLearnFront.setOnClickListener(this);

        tvNext.setOnClickListener(this);
        tvPrev.setOnClickListener(this);
    }

    @Override
    public void displayError(String message) {
        toast(this, message);
    }

    @Override
    public void displayError(int stringResID) {
        toast(this, stringResID);
    }

    @Override
    public void setToolbarData(String setName) {
        if (getSupportActionBar() != null)
            getSupportActionBar().setTitle(setName);
    }

    @Override
    public void showCard(FlashCard flashCard, SetDetailContract.FrontViewMode frontViewMode) {

        if (flipView.isBackSide()) {
            flipView.flipTheView();
        }

        ivFCImageBack.setImageBitmap(BitmapUtils.fromByteArray(flashCard.getImage()));
        ivFCImageFront.setImageBitmap(BitmapUtils.fromByteArray(flashCard.getImage()));

        tvFCWordBack.setText(flashCard.getWord());
        tvWordFront.setText(flashCard.getWord());

        tvLangugeBack.setText(LanguageUtils.getLanguageByCode(flashCard.getLanguage()));
        tvLanguageFront.setText(LanguageUtils.getLanguageByCode(flashCard.getLanguage()));

        if (frontViewMode == SetDetailContract.FrontViewMode.MODE_IMAGE_ONLY) {
            show(ivFCImageFront);
            dismiss(tvWordFront);
        } else {
            show(tvWordFront);
            dismiss(ivFCImageFront);
        }
    }

    @Override
    public void showLayoutSpeakLoading() {
        show(pbLoadingAudioBack);
        dismiss(ivSpeakBack);
        btnSpeakBack.setClickable(false);

        show(pbLoadingAudioFront);
        dismiss(ivSpeakFront);
        btnSpeakFront.setClickable(false);
    }

    @Override
    public void showLayoutIsSpeaking() {
        dismiss(pbLoadingAudioBack);
        show(ivSpeakBack);
        ivSpeakBack.setImageDrawable(getDrawable(R.drawable.ic_stop));
        btnSpeakBack.setClickable(false);

        dismiss(pbLoadingAudioFront);
        show(ivSpeakFront);
        ivSpeakFront.setImageDrawable(getDrawable(R.drawable.ic_stop));
        btnSpeakFront.setClickable(false);
    }

    @Override
    public void refreshLayoutSpeak() {
        dismiss(pbLoadingAudioBack);
        show(ivSpeakBack);
        ivSpeakBack.setImageDrawable(getDrawable(R.drawable.ic_speak));
        btnSpeakBack.setClickable(true);

        dismiss(pbLoadingAudioFront);
        show(ivSpeakFront);
        ivSpeakFront.setImageDrawable(getDrawable(R.drawable.ic_speak));
        btnSpeakFront.setClickable(true);
    }


    @Override
    public void updatePageNumber(int currentPage, int totalPage) {
        tvPage.setText(getString(R.string.page, currentPage, totalPage));
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.flipView:
                flipView.flipTheView();
                break;
            case R.id.layout_speak_back:
            case R.id.layout_speak_front:
                // Speak
                mPresenter.speakWord();
                break;
            case R.id.btn_is_learned_back:
            case R.id.btn_is_learned_front:
                // Mark is learn
                break;
            case R.id.tv_next:
                mPresenter.nextCard();
                break;
            case R.id.tv_prev:
                mPresenter.previousCard();
                break;
        }
    }
}