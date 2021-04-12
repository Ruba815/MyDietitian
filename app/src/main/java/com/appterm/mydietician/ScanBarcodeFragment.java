package com.appterm.mydietician;

import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

import java.util.ArrayList;
import java.util.List;

import me.dm7.barcodescanner.zbar.BarcodeFormat;
import me.dm7.barcodescanner.zbar.Result;
import me.dm7.barcodescanner.zbar.ZBarScannerView;
import me.dm7.barcodescanner.zxing.ZXingScannerView;

import static android.hardware.camera2.CaptureResult.FLASH_STATE;


public class ScanBarcodeFragment extends Fragment{

    private ZBarScannerView mScannerView;

    ArrayList<Product> products = new ArrayList<>();
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_scan_barcode, container, false);
        Button scan = root.findViewById(R.id.scan);
        initData();
        scan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

//            Intent intent = new Intent(v.getContext(),ScannerActivity.class);
//            startActivityForResult(intent,123);

               // new IntentIntegrator(getActivity()).initiateScan();
                IntentIntegrator.forSupportFragment(ScanBarcodeFragment.this).initiateScan();
            }
        });


        return root;

    }
    private void initData(){
        products.add(new Product("شبس","شبس","135kcal","12gm"));
        products.add(new Product("apple","apple","150kcal","12gm"));
        products.add(new Product("panana","orange","135kcal","12gm"));
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        IntentResult result = IntentIntegrator.parseActivityResult(requestCode, resultCode, data);
        if(result != null) {
            if(result.getContents() == null) {
              ////  Toast.makeText(this, "Cancelled", Toast.LENGTH_LONG).show();
            } else {
              ///  Toast.makeText(this, "Scanned: " + result.getContents(), Toast.LENGTH_LONG).show();
               // if (data.hasExtra("code")) {
                    String qr = result.getContents();
                    Toast.makeText(getActivity(), "Content", Toast.LENGTH_SHORT).show();
                    for (Product product : products){
                        Log.e("Product",product.getName());
                        if (product.getQr().equals(qr)){
                            new AlertDialog.Builder(getActivity())
                                    .setTitle(product.getName())
                                    .setMessage("the"+product.getKcl()+"\n and"+product.getGram())

                                    // Specifying a listener allows you to take an action before dismissing the dialog.
                                    // The dialog is automatically dismissed when a dialog button is clicked.
                                    .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                        public void onClick(DialogInterface dialog, int which) {

                                        }
                                    })
                                    .show();
                        }
                    }
                //}
            }
        }
        super.onActivityResult(requestCode, resultCode, data);

    }
}