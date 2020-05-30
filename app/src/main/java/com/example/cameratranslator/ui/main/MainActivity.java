package com.example.cameratranslator.ui.main;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.graphics.Typeface;
import android.net.Uri;
import android.util.Log;
import android.util.Size;
import android.view.Surface;
import android.view.TextureView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.camera.core.CameraX;
import androidx.camera.core.ImageCapture;
import androidx.camera.core.ImageCaptureConfig;
import androidx.camera.core.Preview;
import androidx.camera.core.PreviewConfig;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.example.cameratranslator.R;
import com.example.cameratranslator.base.BaseActivity;
import com.example.cameratranslator.navigation.Navigation;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import static com.example.cameratranslator.utils.ViewUtils.toast;

public class MainActivity extends BaseActivity<MainPresenter> implements MainContract.View, View.OnClickListener {

    private final static int ACCESS_CAMERA_REQ = 101;
    private final static int READ_WRITE_STORATE_REQ = 432;

    private String[] CAMERA_REQUIRED_PERMISSIONS = new String[]{
            Manifest.permission.CAMERA,
    };

    private String[] PICK_IMAGE_REQUIRED_PERMISSIONS = new String[]{
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE,
    };

    private TextureView viewFinder;
    private ImageButton btnCapture, btnGallery, btnFlip;
    private ImageView btnSetting;
    private TextView tvText, tvObject;

    private ExecutorService executor = Executors.newSingleThreadExecutor();

    @Override
    protected int getLayoutID() {
        return R.layout.activity_main;
    }

    @Override
    protected MainPresenter initPresenter() {
        return new MainPresenter();
    }

    @Override
    protected void onCreate() {

        initViewComponent();
        implementListeners();

        if (allPermissionGranted()) {
            viewFinder.post(this::startCamera);
        } else {
            ActivityCompat.requestPermissions(
                    this,
                    CAMERA_REQUIRED_PERMISSIONS,
                    ACCESS_CAMERA_REQ
            );
        }

        mPresenter.changeToObjectMode();

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == MainContract.PICK_IMG_REQ) {
            if (resultCode == RESULT_OK) {
                try {
                    Uri imageUri = data.getData();
                    InputStream is = getContentResolver().openInputStream(imageUri);
                    Bitmap bitmap = BitmapFactory.decodeStream(is);
                } catch (NullPointerException | FileNotFoundException e) {
                    e.printStackTrace();
                    toast(MainActivity.this, R.string.something_went_wrong);
                }
            }
        }
    }

    protected void initViewComponent() {
        viewFinder = findViewById(R.id.view_finder);

        btnSetting = findViewById(R.id.btn_setting);

        btnCapture = findViewById(R.id.btn_capture);
        btnFlip = findViewById(R.id.btn_flip);
        btnGallery = findViewById(R.id.btn_gallery);

        tvText = findViewById(R.id.tv_text);
        tvObject = findViewById(R.id.tv_object);
    }

    protected void implementListeners() {
        // Every time the provided texture view changes, recompute layout
        viewFinder.addOnLayoutChangeListener((v, left, top, right, bottom, oldLeft, oldTop, oldRight, oldBottom) -> updateTransform());

        btnSetting.setOnClickListener(this);

        tvText.setOnClickListener(this);
        tvObject.setOnClickListener(this);

        btnGallery.setOnClickListener(this);
        btnFlip.setOnClickListener(this);
    }

    private void startCamera() {
        // Create configuration object for the viewfinder use case
        Size screen = new Size(viewFinder.getWidth(), viewFinder.getHeight());

        PreviewConfig previewConfig = new PreviewConfig
                .Builder()
//                .setTargetAspectRatio(AspectRatio.RATIO_16_9)
                .setTargetResolution(screen)
                .build();
        Preview preview = new Preview(previewConfig);

        preview.setOnPreviewOutputUpdateListener(output -> {
            ViewGroup parent = (ViewGroup) viewFinder.getParent();
            parent.removeView(viewFinder);
            parent.addView(viewFinder, 0);

            viewFinder.setSurfaceTexture(output.getSurfaceTexture());
            updateTransform();
        });

        // Create configuration object for the image capture use case
        ImageCaptureConfig imageCaptureConfig = new ImageCaptureConfig
                .Builder()
                .setCaptureMode(ImageCapture.CaptureMode.MIN_LATENCY)
                .setTargetResolution(screen)
//                .setTargetAspectRatio(AspectRatio.RATIO_16_9)
                .setTargetRotation(getWindowManager().getDefaultDisplay().getRotation())
                .build();

        // Build the image capture use case and attach button click listener
        ImageCapture imageCapture = new ImageCapture(imageCaptureConfig);
        btnCapture.setOnClickListener(v -> {
            // Disable clickability
            btnCapture.setClickable(false);

            // Create an image file
            File file = new File(
                    getExternalMediaDirs()[0],
                    System.currentTimeMillis() + ".jpg"
            );

            // Take the picture
            imageCapture.takePicture(file, executor, new ImageCapture.OnImageSavedListener() {
                @Override
                public void onImageSaved(@NonNull File file) {
                    viewFinder.post(() -> {
//                                Bitmap bitmap = BitmapHelper.getBitmapFromPath(file.getAbsolutePath());
                                mPresenter.toDetectActivity(MainActivity.this, file.getAbsolutePath());
                            }
                    );

                    // Enable clickability
                    btnCapture.setClickable(true);
                }

                @Override
                public void onError(@NonNull ImageCapture.ImageCaptureError imageCaptureError, @NonNull String message, @Nullable Throwable cause) {
                    // Display error
                    String msg = "Photo capture failed: " + message;
                    viewFinder.post(() -> toast(getBaseContext(), msg));

                    // Enable clickability
                    btnCapture.setClickable(true);
                }
            });
        });

        // Bind use cases to lifecycle
        // If Android Studio complains about "this" being not a LifecycleOwner
        // try rebuilding the project or updating the appcompat dependency to
        // version 1.1.0 or higher.
        CameraX.bindToLifecycle(this, preview, imageCapture);
    }

    private void updateTransform() {
        Matrix matrix = new Matrix();

        // Compute the center of the view finder
        float centerX = viewFinder.getWidth() / 2f;
        float centerY = viewFinder.getHeight() / 2f;

        // Correct preview output to account for display rotation
        int rotationDegrees;
        switch (viewFinder.getDisplay().getRotation()) {
            case Surface.ROTATION_0:
                rotationDegrees = 0;
                break;
            case Surface.ROTATION_90:
                rotationDegrees = 90;
                break;
            case Surface.ROTATION_180:
                rotationDegrees = 180;
                break;
            case Surface.ROTATION_270:
                rotationDegrees = 270;
                break;
            default:
                return;
        }
        matrix.postRotate((float) -rotationDegrees, centerX, centerY);

        // Finally, apply transformations to our TextureView
        viewFinder.setTransform(matrix);
    }

    @Override
    public void highlightTextTab() {
        tvText.setTypeface(Typeface.DEFAULT_BOLD);
        tvText.setTextColor(ContextCompat.getColor(this, R.color.colorPrimaryDark));
        tvText.setBackgroundColor(ContextCompat.getColor(this, R.color.colorPrimary));

        tvObject.setTypeface(Typeface.DEFAULT);
        tvObject.setTextColor(ContextCompat.getColor(this, R.color.colorWhite));
        tvObject.setBackgroundColor(ContextCompat.getColor(this, R.color.colorPrimaryDark));
    }

    @Override
    public void highlightObjectTab() {
        tvObject.setTypeface(Typeface.DEFAULT_BOLD);
        tvObject.setTextColor(ContextCompat.getColor(this, R.color.colorPrimaryDark));
        tvObject.setBackgroundColor(ContextCompat.getColor(this, R.color.colorPrimary));

        tvText.setTypeface(Typeface.DEFAULT);
        tvText.setTextColor(ContextCompat.getColor(this, R.color.colorWhite));
        tvText.setBackgroundColor(ContextCompat.getColor(this, R.color.colorPrimaryDark));
    }

    @Override
    public void pickImage() {
        ActivityCompat.requestPermissions(
                MainActivity.this,
                PICK_IMAGE_REQUIRED_PERMISSIONS,
                READ_WRITE_STORATE_REQ
        );
    }

    /**
     * Process result from permission request dialog box, has the request
     * been granted? If yes, start Camera. Otherwise display a toast
     */
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == ACCESS_CAMERA_REQ) {
            if (allPermissionGranted()) {
                viewFinder.post(() -> startCamera());
            } else {
                toast(
                        this,
                        "Permissions not granted by the user."
                );
            }
        }

        if (requestCode == READ_WRITE_STORATE_REQ) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
//                Navigation.toPickImageFromGalleryActivity(this, MainContract.PICK_IMG_REQ);
                if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED &&
                        ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED)
                    Navigation.toPickImageActivity(this);
                else
                    toast(this, "Permission denied 2");
            } else {
                toast(this, "Permission denied xxx");
            }
        }
    }

    /**
     * Check if all permission specified in the manifest have been granted
     */
    private boolean allPermissionGranted() {
        for (String permission : CAMERA_REQUIRED_PERMISSIONS) {
            if (ContextCompat.checkSelfPermission(this, permission) != PackageManager.PERMISSION_GRANTED) {
                return false;
            }
        }

        return true;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_setting:
                Navigation.toSettingActivity(this);
                break;
            case R.id.tv_text:
                mPresenter.changeToTextMode();
                break;
            case R.id.tv_object:
                mPresenter.changeToObjectMode();
                break;
            case R.id.btn_gallery:
                pickImage();
                break;
        }
    }
}
