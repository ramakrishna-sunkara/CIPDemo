package poc.cip.cgs.com.cip_poc;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;

import com.google.zxing.ResultPoint;
import com.journeyapps.barcodescanner.BarcodeCallback;
import com.journeyapps.barcodescanner.BarcodeResult;
import com.journeyapps.barcodescanner.CompoundBarcodeView;

import java.util.List;


public class CameraActivity extends Activity {
   private String qrcode;
    private String result;
    private static final String TAG = CameraActivity.class.getSimpleName();
    private CompoundBarcodeView barcodeView;

    private BarcodeCallback callback = new BarcodeCallback() {
        @Override
        public void barcodeResult(BarcodeResult resultp) {
            if (resultp.getText() != null) {
                String qrcode = resultp.getText();
                barcodeView.setStatusText(qrcode);
                Log.d("OUTPUT", qrcode);
                result=qrcode;
            }
            //Added preview of scanned barcode
//            ImageView imageView = (ImageView) findViewById(R.id.barcodePreview);
//            imageView.setImageBitmap(result.getBitmapWithResultPoints(Color.YELLOW));


        }

        @Override
        public void possibleResultPoints(List<ResultPoint> resultPoints) {
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.continuous_scan);

        barcodeView = (CompoundBarcodeView) findViewById(R.id.barcode_scanner);
        barcodeView.decodeContinuous(callback);
    }

    @Override
    protected void onResume() {
        super.onResume();

        barcodeView.resume();
    }

    @Override
    protected void onPause() {
        super.onPause();

        barcodeView.pause();
    }

    public void pause(View view) {
        barcodeView.pause();
    }

    public void resume(View view) {
        barcodeView.resume();
    }

    public void triggerScan(View view) {
        barcodeView.decodeSingle(callback);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        return barcodeView.onKeyDown(keyCode, event) || super.onKeyDown(keyCode, event);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent intent = new Intent(this, CustomerDetailActivity.class);
        intent.putExtra("qrcode",result);
        System.out.println("codeeeeeeeeeeee"+result);
        startActivity(intent);
    }
}
