package com.xuber_for_services.provider.KYC_Activity;

import android.Manifest;
import android.app.Activity;
import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Environment;
import android.os.StrictMode;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.design.widget.Snackbar;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.TimeoutError;
import com.android.volley.VolleyError;
//import com.koushikdutta.async.http.body.FileBody;
import com.xuber_for_services.provider.Activity.Home;
import com.xuber_for_services.provider.Activity.SplashScreen;
import com.xuber_for_services.provider.Constant.URLHelper;
import com.xuber_for_services.provider.Helper.AppHelper;
import com.xuber_for_services.provider.Helper.ConnectionHelper;
import com.xuber_for_services.provider.Helper.CustomDialog;
import com.xuber_for_services.provider.Helper.Sessionwa;
import com.xuber_for_services.provider.Helper.SharedHelper;
import com.xuber_for_services.provider.Helper.VolleyMultipartRequest;
import com.xuber_for_services.provider.R;
import com.xuber_for_services.provider.Utils.Utilities_Multipart;
import com.xuber_for_services.provider.Utils.Utility;
import com.xuber_for_services.provider.XuberServicesApplication;

import org.apache.http.entity.mime.HttpMultipartMode;
import org.apache.http.entity.mime.MultipartEntity;
import org.apache.http.entity.mime.content.FileBody;
import org.apache.http.entity.mime.content.StringBody;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URISyntaxException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

public class KycActivity extends AppCompatActivity {
    EditText serviceName, area, upto, pinCode, employee, code, designation,
    aadhar,bank,pan,bankAc,ifsc,branch;

    Spinner group;

    String  Address, City, Distict, Pin, PresentAddress,
            City1,Distict1, Pin1, ServiceName, Contact, Email, Area, Upto, PinCode, Employee, Code, Designation_A,
            Group, Contact1,Aadhar,Bank,Pan,BankAc,Ifsc,Branch;
    String RegNo, Datee, ProName, FatherName, MotherName, Dob, Proof, IdNo;

    Button update, btUpload;
    Sessionwa sessionwa;
    CustomDialog customDialog;
    ImageView imgBack;
    TextView lblSave, lblUpd;
    Boolean isImageChanged = false;
    private static final int SELECT_PHOTO = 100;
    public Activity activity = KycActivity.this;
    Boolean isInternet;
    ConnectionHelper helper;
    final Calendar myCalendar = Calendar.getInstance();
    //ImageView calender, calender1;
     String result;
     File destination;

//    private int REQUEST_CAMERA = 0, SELECT_FILE = 1;
//    String userChoosenTask;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            getWindow().clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            getWindow().setStatusBarColor(ContextCompat.getColor(this, R.color.colorPrimary));
            sessionwa = new Sessionwa(this);
            getWindow().setNavigationBarColor(ContextCompat.getColor(this, R.color.colorPrimary));
        }
        setContentView(R.layout.activity_kyc);


        if (Build.VERSION.SDK_INT > 15) {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);
        }


        serviceName = (EditText) findViewById(R.id.serviceName);
        area = (EditText) findViewById(R.id.area);
        upto = (EditText) findViewById(R.id.upto);
        pinCode = (EditText) findViewById(R.id.pinCode);
        employee = (EditText) findViewById(R.id.employee);
        code = (EditText) findViewById(R.id.code);
        designation = (EditText) findViewById(R.id.designation);

        group = (Spinner) findViewById(R.id.group);

        lblSave = (TextView) findViewById(R.id.lblSave);
        lblUpd = (TextView) findViewById(R.id.lblUpd);
        imgBack = (ImageView) findViewById(R.id.imgBack);
        update = (Button) findViewById(R.id.update);

        btUpload = (Button) findViewById(R.id.btUpload);


        aadhar = (EditText) findViewById(R.id.aadhar);
        bank = (EditText) findViewById(R.id.bank);
        pan = (EditText) findViewById(R.id.pan);
        bankAc = (EditText) findViewById(R.id.bankAc);
        ifsc = (EditText) findViewById(R.id.ifsc);
        branch = (EditText) findViewById(R.id.branch);
        //    isInternet = helper.isConnectingToInternet();
        //******************************************************************

        RegNo=getIntent().getStringExtra("RegNo");
        Datee=getIntent().getStringExtra("Datee");
        ProName=getIntent().getStringExtra("ProName");
        FatherName=getIntent().getStringExtra("FatherName");
        MotherName=getIntent().getStringExtra("MotherName");
        Dob=getIntent().getStringExtra("Dob");
        Proof=getIntent().getStringExtra("Proof");
        IdNo=getIntent().getStringExtra("IdNo");
        destination= (File) getIntent().getSerializableExtra("destination");

        Address=getIntent().getStringExtra("Address");
        City=getIntent().getStringExtra("City");
        Distict=getIntent().getStringExtra("Distict");
        Pin=getIntent().getStringExtra("Pin");
        PresentAddress=getIntent().getStringExtra("PresentAddress");
        City1=getIntent().getStringExtra("City1");
        Distict1=getIntent().getStringExtra("Distict1");
        Pin1=getIntent().getStringExtra("Pin1");
        Contact=getIntent().getStringExtra("Contact");
        Contact1=getIntent().getStringExtra("Contact1");
        Email=getIntent().getStringExtra("Email");

        Log.e("img_destination",destination.toString());
        //*******************************************************************

       /* pan.setFilters(new InputFilter[]{new InputFilter.AllCaps()});
        ifsc.setFilters(new InputFilter[]{new InputFilter.AllCaps()});*/

        lblSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

//                Address=address.getText().toString();
//                City=city.getText().toString();
//                Distict=distict.getText().toString();
//                Pin=pin.getText().toString();
//                PresentAddress=presentAddress.getText().toString();
//                City1=city1.getText().toString();
//                Distict1=distict1.getText().toString();
//                Pin1=pin1.getText().toString();
                ServiceName=serviceName.getText().toString();
//                Contact=contact.getText().toString();
//                Email=email.getText().toString();
                Area=area.getText().toString();
                Upto=upto.getText().toString();
                PinCode=pinCode.getText().toString();
                Employee=employee.getText().toString();
                Code=code.getText().toString();
                Designation_A=designation.getText().toString();
                Group=group.getSelectedItem().toString();
                Aadhar=aadhar.getText().toString();
                Bank=bank.getText().toString();
                Pan=pan.getText().toString();
                BankAc=bankAc.getText().toString();
                Ifsc=ifsc.getText().toString();
                Branch=branch.getText().toString();

//                String RegNo, Datee, ProName, FatherName, MotherName, Dob, Proof, IdNo, Address, City, Distict, Pin,
//                        PresentAddress,
//                        City1,Distict1, Pin1, ServiceName, Contact, Email, Area, Upto, PinCode, Employee, Code,
//                        Designation_A,
//                        Group, Contact1,Aadhar,Bank,Pan,BankAc,Ifsc,Branch;

                if (!RegNo.isEmpty() && !Datee.isEmpty() && !ProName.isEmpty() && !FatherName.isEmpty()&& !MotherName.isEmpty()
                        && !Dob.isEmpty()
                        && !Proof.isEmpty()&& !IdNo.isEmpty()&&
                        !Address.isEmpty()&& !City.isEmpty()&& !Distict.isEmpty()&&
                        !Pin.isEmpty()
                        && !PresentAddress.isEmpty()&& !City1.isEmpty()&& !Distict1.isEmpty()&& !Pin1.isEmpty()&&
                        !ServiceName.isEmpty()&& !Contact.isEmpty()&& !Email.isEmpty()
                        && !Area.isEmpty()&& !Upto.isEmpty()&& !PinCode.isEmpty()&& !Employee.isEmpty()&& !Code.isEmpty()&&
                        !Designation_A.isEmpty()
                        && !Group.isEmpty()&& !Contact1.isEmpty()&& !Aadhar.isEmpty()&& !Bank.isEmpty()&& !Pan.isEmpty()&&
                        !BankAc.isEmpty()
                        && !Ifsc.isEmpty()&& !Branch.isEmpty()
                ) {
                    if (destination==null){
                        displayMessage("Please choose ID Proofe Image Form 1");
                    }else {
                        // KYCRegister();

                        new KycRegister().execute();
                    }
                }
                else {
                   displayMessage("Please enter all field");
                    Toast.makeText(KycActivity.this, "All Field Required", Toast.LENGTH_SHORT).show();
                }


              /*  if (lblSave.getText().toString().equalsIgnoreCase("Edit")) {
                    lblSave.setText("Save");

                } else {
                    lblSave.setText("Edit");

                    Pattern ps = Pattern.compile(".*[0-9].*");

                    updateProfile();
                }
*/

            }
        });

        lblUpd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

//                Address=address.getText().toString();
//                City=city.getText().toString();
//                Distict=distict.getText().toString();
//                Pin=pin.getText().toString();
//                PresentAddress=presentAddress.getText().toString();
//                City1=city1.getText().toString();
//                Distict1=distict1.getText().toString();
//                Pin1=pin1.getText().toString();
               ServiceName=serviceName.getText().toString();
//                Contact=contact.getText().toString();
//                Email=email.getText().toString();
                Area=area.getText().toString();
                Upto=upto.getText().toString();
                PinCode=pinCode.getText().toString();
                Employee=employee.getText().toString();
                Code=code.getText().toString();
                Designation_A=designation.getText().toString();
                Group=group.getSelectedItem().toString();
                Aadhar=aadhar.getText().toString();
                Bank=bank.getText().toString();
                Pan=pan.getText().toString();
                BankAc=bankAc.getText().toString();
                Ifsc=ifsc.getText().toString();
                Branch=branch.getText().toString();

                if (!(SharedHelper.getKey(KycActivity.this,"kyc_update").isEmpty())){
                    Log.e("kyc_update_value",""+SharedHelper.getKey(
                            KycActivity.this,"kyc_update"));


                    if ( !ServiceName.isEmpty()&& !Area.isEmpty()&& !Upto.isEmpty()&& !PinCode.isEmpty()&& !Employee.isEmpty()&& !Code.isEmpty()&&
                            !Designation_A.isEmpty()
                            && !Group.isEmpty()&& !Contact1.isEmpty()&& !Aadhar.isEmpty()&& !Bank.isEmpty()&& !Pan.isEmpty()&&
                            !BankAc.isEmpty()
                            && !Ifsc.isEmpty()&& !Branch.isEmpty()
                    )
                    {
                        new KycActivity.KYC_Update().execute();
                    }

                    else {
                        displayMessage("Please enter all field");
                        Toast.makeText(KycActivity.this, "All Field Required", Toast.LENGTH_SHORT).show();
                    }
                }else {
                    //displayMessage("Please save first");
                    Toast.makeText(KycActivity.this, "Please save first", Toast.LENGTH_SHORT).show();
                }




            }
        });

//        final DatePickerDialog.OnDateSetListener date = new DatePickerDialog.OnDateSetListener() {
//
//            @Override
//            public void onDateSet(DatePicker view, int year, int monthOfYear,
//                                  int dayOfMonth) {
//                // TODO Auto-generated method stub
//                myCalendar.set(Calendar.YEAR, year);
//                myCalendar.set(Calendar.MONTH, monthOfYear);
//                myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
//                updateLabel();
//            }
//
//        };
//
//        calender.setOnClickListener(new View.OnClickListener() {
//
//            @Override
//            public void onClick(View v) {
//                // TODO Auto-generated method stub
//                new DatePickerDialog(KycActivity.this, date, myCalendar
//                        .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
//                        myCalendar.get(Calendar.DAY_OF_MONTH)).show();
//            }
//        });
//
//        final DatePickerDialog.OnDateSetListener date1 = new DatePickerDialog.OnDateSetListener() {
//
//            @Override
//            public void onDateSet(DatePicker view, int year, int monthOfYear,
//                                  int dayOfMonth) {
//                // TODO Auto-generated method stub
//                myCalendar.set(Calendar.YEAR, year);
//                myCalendar.set(Calendar.MONTH, monthOfYear);
//                myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
//                updateLabel1();
//            }
//
//        };
//
//        calender1.setOnClickListener(new View.OnClickListener() {
//
//            @Override
//            public void onClick(View v) {
//                // TODO Auto-generated method stub
//                new DatePickerDialog(KycActivity.this, date1, myCalendar
//                        .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
//                        myCalendar.get(Calendar.DAY_OF_MONTH)).show();
//            }
//        });



        imgBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(KycActivity.this, Kyc2_Activity.class);
                startActivity(intent);
                finish();
                /* onBackPressed();*/
            }
        });
    }

    //*****************************************************************************************
//    private void updateLabel() {
//        String myFormat = "dd/MM/yyyy"; //In which you need put here
//        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);
//
//        datee.setText(sdf.format(myCalendar.getTime()));
//    }
//
//    private void updateLabel1() {
//        String myFormat = "dd/MM/yyyy"; //In which you need put here
//        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);
//
//        dob.setText(sdf.format(myCalendar.getTime()));
//    }
    //********************************************************************************************

    private void KYCRegister() {

        isImageChanged = false;
        customDialog = new CustomDialog(KycActivity.this);
        customDialog.setCancelable(false);
        customDialog.show();

        VolleyMultipartRequest volleyMultipartRequest = new VolleyMultipartRequest(Request.Method.POST,
                URLHelper.IMG_PROFILE, new Response.Listener<NetworkResponse>() {
            @Override
            public void onResponse(NetworkResponse response) {
                customDialog.dismiss();
                String res = new String(response.data);

                Log.e("responce", response.toString());
                Log.e("responce_object", res);
                try {
                    JSONObject jsonObject = new JSONObject(res);

                    Log.e("responce", response.toString());
                    Log.e("responce json_object", jsonObject.toString());
                    Log.e("responce_object", res);


                    // JSONObject id = response.optJSONObject("id");

                    sessionwa.setLogin(true);
                    Intent resetIntent = new Intent(KycActivity.this, Home.class);
                    startActivity(resetIntent);
                    finish();


                    //  displayMessage(getString(R.string.update_success));
                    Log.e("display message", "" + getString(R.string.update_success));


                } catch (JSONException e) {
                    e.printStackTrace();
                }


            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                customDialog.dismiss();
                Log.e("KycActivity", "" + error.getLocalizedMessage());
                Log.e("KycActivity", "" + error.getMessage());
                Log.e("KycActivity", "" + error.networkResponse);
                String json = null;
                NetworkResponse response = error.networkResponse;
                if (error.networkResponse == null) {
                    if (error.getClass().equals(TimeoutError.class)) {
                        // Show timeout error message
                        // updateProfileWithoutImage();
                        Log.e("rrrr", "dddd");
                    } else {
                        // displayMessage(getString(R.string.something_went_wrong));
                        Log.e("display message", "" + getString(R.string.something_went_wrong));
                    }
                }
                else if (response.statusCode == 400 || response.statusCode == 405 || response.statusCode == 500) {
                    try {
                        Log.e("error123",""+response.statusCode);
                        Log.e("error123hh",""+error.toString());
                       // displayMessage(error.optString("error"));

                    } catch (Exception e) {
                        displayMessage(getString(R.string.something_went_wrong));
                    }
                } else if (response.statusCode == 404) {
                    Log.e("display404", response.toString());
                    Log.e("display404hi", error.toString());

                }

                else if (response.statusCode == 422) {

                    json = XuberServicesApplication.trimMessage(new String(response.data));
                    if (json != "" && json != null) {
                        // displayMessage(json);
                        Log.e("display", json);
                        Log.e("display_code", ""+response.statusCode);
                    } else {
                        // displayMessage("Please try again.");
                        Log.e("display try again", "try again");
                    }

                } else {
                    //displayMessage(getString(R.string.something_went_wrong));
                    Log.e("display message", "" + getString(R.string.something_went_wrong));
                }

            }
        }) {
            @Override
            public Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();


                params.put("form_no", "22333");
//                params.put("reg_no", regNo.getText().toString());
//                params.put("date_application", datee.getText().toString());
//                params.put("provider_name", proName.getText().toString());
//                params.put("father", fatherName.getText().toString());
//                params.put("mother", motherName.getText().toString());
//                params.put("dob", dob.getText().toString());
//                params.put("proof", proof.getSelectedItem().toString());
//                params.put("id_no", idNo.getText().toString());
//                params.put("address", address.getText().toString());
//                params.put("city", city.getText().toString());
//                params.put("dist", distict.getText().toString());
//                params.put("pin", pin.getText().toString());
//                params.put("present_address", presentAddress.getText().toString());
//                params.put("city_1", city1.getText().toString());
//                params.put("dist_1", distict1.getText().toString());
//                params.put("pin_1", pin1.getText().toString());
                params.put("service_name", serviceName.getText().toString());
//                params.put("contact", contact.getText().toString());
//                params.put("email", email.getText().toString());
                params.put("area", area.getText().toString());
                params.put("upto", upto.getText().toString());
                params.put("pincode", pinCode.getText().toString());
                params.put("emplyee", employee.getText().toString());
                params.put("code", code.getText().toString());
                params.put("designation", designation.getText().toString());
                params.put("adhar_no", aadhar.getText().toString());
                params.put("pan_card", pan.getText().toString());
                params.put("bank_name", bank.getText().toString());
                params.put("bank_ac_no", bankAc.getText().toString());
                params.put("ifsc_code", ifsc.getText().toString());
                params.put("branch", branch.getText().toString());
                params.put("group", group.getSelectedItem().toString());
               // params.put("contact_1", contact1.getText().toString());

              //  // params.put("id_img", "logo.jpg");


//                params.put("form_no", "223355463");
//                params.put("reg_no", "1238545");
//                params.put("date_application", "22/05/2019");
//                params.put("provider_name", "raghav");
//                params.put("father", "rrr");
//                params.put("mother", "fffff");
//                params.put("dob", "05/06/2019");
//                params.put("proof", "voterid");
//                params.put("id_no", "78954555");
//                params.put("address", "rfgjjj");
//                params.put("city", "indore");
//                params.put("dist", "reqa");
//                params.put("pin", "452011");
//                params.put("present_address","rewa indore");
//                params.put("city_1", "bhopal");
//                params.put("dist_1", "indorer nanda");
//                params.put("pin_1", "452001");
//                params.put("service_name", "plumber");
//                params.put("contact", "8885556660");
//                params.put("email", "rrrr@gmail.com");
//                params.put("area", "tower squre");
//                params.put("upto", "ttttt");
//                params.put("pincode", "452006");
//                params.put("emplyee", "ttttt");
//                params.put("code", "456789");
//                params.put("designation", "nanda nagar");
//                params.put("group", "asl");
//                params.put("contact_1", "5556669998");
//                params.put("adhar_no", "556644778899");
//                params.put("pan_card","EHSPS9872L");
//                params.put("bank_name", "SBI");
//                params.put("bank_ac_no", "141110108865");
//                params.put("ifsc_code", "CNRB0001422");
//                params.put("branch", "Indore");

                Log.e("InputTo_KYC_API", "" + params);

                return params;
            }

            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                HashMap<String, String> headers = new HashMap<String, String>();
                headers.put("X-Requested-With", "XMLHttpRequest");
                headers.put("Authorization", "Bearer" + " " + SharedHelper.getKey(KycActivity.this, "access_token"));

                headers.put("Content-Type", "application/json");
                headers.put("Accept", "application/json");

                Log.e("Authoization", "Bearer" + " "+ SharedHelper.getKey(KycActivity.this, "access_token"));
                return headers;
            }


            @Override
            protected Map<String, DataPart> getByteData() throws AuthFailureError {
                Map<String, DataPart> params = new HashMap<>();
               // params.put("id_img", new DataPart("userImage.jpg", AppHelper.getFileDataFromDrawable(idImg.getDrawable()), "image/jpeg"));
               // params.put("id_img", new DataPart("userImage.jpg", AppHelper.getFileDataFromDrawable(idImg.getDrawable()), "image/jpeg"));

              //  Log.e("id_img", "" + AppHelper.getFileDataFromDrawable(idImg.getDrawable()));
                return params;
            }
        };
        XuberServicesApplication.getInstance().addToRequestQueue(volleyMultipartRequest);
    }

//    //------------------------------------------------------------
//
//    public void goToImageIntent() {
////        Intent intent = new Intent();
////        intent.setType("image/*");
////        intent.setAction(Intent.ACTION_GET_CONTENT);
////        startActivityForResult(Intent.createChooser(intent, "Select Picture"), SELECT_PHOTO);
//
//
//        final CharSequence[] items = { "Take Photo", "Choose from Library",
//                "Cancel" };
//
//        android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(KycActivity.this);
//        builder.setTitle("Add Photo!");
//        builder.setItems(items, new DialogInterface.OnClickListener() {
//            @Override
//            public void onClick(DialogInterface dialog, int item) {
//                boolean result= Utility.checkPermission(KycActivity.this);
//
//                if (items[item].equals("Take Photo")) {
//                    userChoosenTask ="Take Photo";
//                    if(result)
//                        cameraIntent();
//
//                } else if (items[item].equals("Choose from Library")) {
//                    userChoosenTask ="Choose from Library";
//                    if(result)
//                        galleryIntent();
//
//                } else if (items[item].equals("Cancel")) {
//                    dialog.dismiss();
//                }
//            }
//        });
//        builder.show();
//
//
//    }
//
//    private void galleryIntent() {
//        Intent i = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
//        // Start new activity with the LOAD_IMAGE_RESULTS to handle back the results when image is picked from the Image Gallery.
//        startActivityForResult(i, SELECT_FILE);
//
//    }
//
//    private void cameraIntent() {
//        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
//        startActivityForResult(intent, REQUEST_CAMERA);
//    }
//
//
//    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
//    private boolean checkStoragePermission() {
//        return ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE)
//                != PackageManager.PERMISSION_GRANTED;
//    }
//
//    @Override
//    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
//        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
//        if (requestCode == 100)
//            for (int grantResult : grantResults)
//                if (grantResult == PackageManager.PERMISSION_GRANTED)
//                    goToImageIntent();
//    }
//
////    @Override
////    public void onActivityResult(int requestCode, int resultCode, Intent data) {
////        super.onActivityResult(requestCode, resultCode, data);
////        if (requestCode == SELECT_PHOTO && resultCode == RESULT_OK && data != null && data.getData() != null) {
////
//////            try {
//////                //Uri uri = data.getData();
//////                //bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), uri);
//////                Bitmap bitmap = null;
//////                if (data.getData() == null) {
//////                    bitmap = (Bitmap) data.getExtras().get("data");
//////                } else {
//////                    bitmap = MediaStore.Images.Media.getBitmap(activity.getContentResolver(), data.getData());
//////                }
//////                idImg.setImageBitmap(bitmap);
//////                isImageChanged = true;
//////            } catch (IOException e) {
//////                e.printStackTrace();
//////            }
////
////            //*****************************************************************************
////
////            Bitmap bm=null;
////            if (data != null) {
////
////                Uri pickedImage = data.getData();
////                String[] filePath = {MediaStore.Images.Media.DATA};
////                Cursor cursor = getContentResolver().query(pickedImage, filePath, null, null, null);
////                cursor.moveToFirst();
////                destination = new File(cursor.getString(cursor.getColumnIndex(filePath[0])));
////                cursor.close();
////
////                Toast.makeText(this, "img "+destination, Toast.LENGTH_SHORT).show();
////                Log.e("gal_image",""+designation);
////                try {
////                    bm = MediaStore.Images.Media.getBitmap(getApplicationContext().getContentResolver(), data.getData());
////                } catch (IOException e) {
////                    e.printStackTrace();
////                }
////            }
////
////            idImg.setImageBitmap(bm);
////        }
////    }
//
//
//    @Override
//    public void onActivityResult(int requestCode, int resultCode, Intent data) {
//        super.onActivityResult(requestCode, resultCode, data);
//
//        if (resultCode == Activity.RESULT_OK) {
//            if (requestCode == SELECT_FILE) {
//                try {
//                    onSelectFromGalleryResult(data);
//                } catch (URISyntaxException e) {
//                    e.printStackTrace();
//                }
//            } else if (requestCode == REQUEST_CAMERA)
//                onCaptureImageResult(data);
//        }
//    }
//
//    private void onCaptureImageResult(Intent data) {
//        Bitmap thumbnail = (Bitmap) data.getExtras().get("data");
//        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
//        thumbnail.compress(Bitmap.CompressFormat.JPEG, 90, bytes);
//
//        destination = new File(Environment.getExternalStorageDirectory(),
//                System.currentTimeMillis() + ".jpg");
//
//        FileOutputStream fo;
//        try {
//            destination.createNewFile();
//            fo = new FileOutputStream(destination);
//            if(destination !=null)
//            {
//                // Toast.makeText(this, "path is"+destination.getAbsolutePath(), Toast.LENGTH_SHORT).show();
//            }else {
//                Toast.makeText(this, "something wrong", Toast.LENGTH_SHORT).show();
//            }
//            fo.write(bytes.toByteArray());
//            fo.close();
//        } catch (FileNotFoundException e) {
//            e.printStackTrace();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//
//        idImg.setImageBitmap(thumbnail);
//    }
//
//    @SuppressWarnings("deprecation")
//    private void onSelectFromGalleryResult(Intent data) throws URISyntaxException {
//
//        Bitmap bm=null;
//        if (data != null) {
//
//            Uri pickedImage = data.getData();
//            String[] filePath = {MediaStore.Images.Media.DATA};
//            Cursor cursor = getContentResolver().query(pickedImage, filePath, null, null, null);
//            cursor.moveToFirst();
//            destination = new File(cursor.getString(cursor.getColumnIndex(filePath[0])));
//            cursor.close();
//
//            //Toast.makeText(this, ""+destination, Toast.LENGTH_SHORT).show();
//
//            Log.e("img_destination",""+destination);
//            try {
//                bm = MediaStore.Images.Media.getBitmap(getApplicationContext().getContentResolver(), data.getData());
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
//        }
//
//        idImg.setImageBitmap(bm);
//    }
////*************************************************************************

    public void displayMessage(String toastString) {
        Log.e("displayMessage", "" + toastString);
        /*Snackbar.make(getCurrentFocus(), toastString, Snackbar.LENGTH_SHORT)
                .setAction("Action", null).show();*/
        Snackbar snackbar = Snackbar.make(getCurrentFocus(), toastString, Snackbar.LENGTH_SHORT);
        View snackBarView = snackbar.getView();
        snackBarView.setBackgroundColor(this.getResources().getColor(R.color.black));
        TextView textView = (TextView) snackBarView.findViewById(android.support.design.R.id.snackbar_text);
        textView.setTextColor(this.getResources().getColor(R.color.white));
        snackbar.show();
    }


    //*********************************************************************************************************

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    private class KycRegister extends AsyncTask<Void, Long, String>
    {


        @Override
        protected String doInBackground(Void... params) {
            try {
                org.apache.http.entity.mime.MultipartEntity entity = new MultipartEntity(
                        HttpMultipartMode.BROWSER_COMPATIBLE);

                //***********************************************************************************
                entity.addPart("form_no", new StringBody("123456"));
                entity.addPart("reg_no", new StringBody(RegNo));
                entity.addPart("date_application", new StringBody(Datee));
                entity.addPart("provider_name", new StringBody(ProName));
                entity.addPart("father", new StringBody(FatherName));
                entity.addPart("mother", new StringBody(MotherName));
                entity.addPart("dob", new StringBody(Dob));
                entity.addPart("proof", new StringBody(Proof));
                entity.addPart("id_no", new StringBody(IdNo));
                entity.addPart("address", new StringBody(Address));
                entity.addPart("city", new StringBody(City));
                entity.addPart("dist", new StringBody(Distict));
                entity.addPart("pin", new StringBody(Pin));
                entity.addPart("present_address", new StringBody(PresentAddress));
                entity.addPart("city_1", new StringBody(City1));
                entity.addPart("dist_1", new StringBody(Distict1));
                entity.addPart("pin_1", new StringBody(Pin1));
                entity.addPart("service_name", new StringBody(ServiceName));
                entity.addPart("contact", new StringBody(Contact));
                entity.addPart("email", new StringBody(Email));
                entity.addPart("area", new StringBody(Area));
                entity.addPart("upto", new StringBody(Upto));
                entity.addPart("pincode", new StringBody(PinCode));
                entity.addPart("emplyee", new StringBody(Employee));
                entity.addPart("code", new StringBody(Code));
                entity.addPart("designation", new StringBody(Designation_A));
                entity.addPart("group", new StringBody(Group));
                entity.addPart("contact_1", new StringBody(Contact1));
                entity.addPart("adhar_no", new StringBody(Aadhar));
                entity.addPart("pan_card", new StringBody(Pan));
                entity.addPart("bank_name", new StringBody(Bank));
                entity.addPart("bank_ac_no", new StringBody(BankAc));
                entity.addPart("ifsc_code", new StringBody(Ifsc));
                entity.addPart("branch", new StringBody(Branch));

               entity.addPart("id_img", new FileBody(destination));

//                entity.addPart("form_no", new StringBody("123456"));
//                entity.addPart("reg_no", new StringBody("888888"));
//                entity.addPart("date_application", new StringBody("22/04/1993"));
//                entity.addPart("provider_name", new StringBody("rrrrr"));
//                entity.addPart("father", new StringBody("rrrrr"));
//                entity.addPart("mother", new StringBody("rrrrr"));
//                entity.addPart("dob", new StringBody("14/05/1990"));
//                entity.addPart("proof", new StringBody("Adhar card"));
//                entity.addPart("id_no", new StringBody("78999555"));
//                entity.addPart("address", new StringBody("rrrrr"));
//                entity.addPart("city", new StringBody("rrrrr"));
//                entity.addPart("dist", new StringBody("rrrrr"));
//                entity.addPart("pin", new StringBody("456987"));
//                entity.addPart("present_address", new StringBody("rrrrr"));
//                entity.addPart("city_1", new StringBody("rrrrr"));
//                entity.addPart("dist_1", new StringBody("rrrrr"));
//                entity.addPart("pin_1", new StringBody("897456"));
//                entity.addPart("service_name", new StringBody("rrrrr"));
//                entity.addPart("contact", new StringBody("8889996666"));
//                entity.addPart("email", new StringBody("rrrrr@gmail.com"));
//                entity.addPart("area", new StringBody("rrrrr"));
//                entity.addPart("upto", new StringBody("rrrrr"));
//                entity.addPart("pincode", new StringBody("999999"));
//                entity.addPart("emplyee", new StringBody("rrrrr"));
//                entity.addPart("code", new StringBody("888888"));
//                entity.addPart("designation", new StringBody("rrrrr"));
//                entity.addPart("group", new StringBody("Group"));
//                entity.addPart("contact_1", new StringBody("8889997777"));
//                entity.addPart("adhar_no", new StringBody("888999999954"));
//                entity.addPart("pan_card", new StringBody("WWWWW9872M"));
//                entity.addPart("bank_name", new StringBody("rrrrr"));
//                entity.addPart("bank_ac_no", new StringBody("88899999"));
//                entity.addPart("ifsc_code", new StringBody("RRRRRR5556"));
//                entity.addPart("branch", new StringBody("rrrrr"));

                Log.e("KYC_API", "" + entity);


//***************************************************************
                String heder=SharedHelper.getKey(KycActivity.this, "access_token");
                Log.e("Authorization", "Bearer"+" "+heder);

                result = Utilities_Multipart.postEntityAndFindJson("http://erapidservice.com/public/api/provider/kyc_register_form", entity,heder);


                return result;

            } catch (Exception e) {
                // something went wrong. connection with the server error
                e.printStackTrace();
            }
            return null;
        }


        @Override
        protected void onPostExecute(String result) {
             //Toast.makeText(KycActivity.this, "res is "+result, Toast.LENGTH_LONG).show();

          //  Log.e("result_", result);

            if (result != null) {
                Log.e("result", result);
//                sessionwa.setLogin(true);
//                Intent resetIntent = new Intent(KycActivity.this, Home.class);
//                startActivity(resetIntent);
//                finish();

                try {
                    JSONObject object = new JSONObject(result);
                    String id = object.getString("id");

                    //SharedHelper.setKycId(KycActivity.this,"KYC_ID",id);
                    SharedHelper.putKey(KycActivity.this,"KYC_ID",id);


                    Toast.makeText(KycActivity.this, "Successful", Toast.LENGTH_LONG).show();

                    sessionwa.setLogin(true);
                    Intent intent = new Intent(KycActivity.this, Home.class);
                    startActivity(intent);
                    finish();


                } catch (JSONException e) {
                  //  dialog.dismiss();
                    Toast.makeText(KycActivity.this, e.toString(), Toast.LENGTH_SHORT).show();
                }

            } else {
                //dialog.dismiss();
                  Toast.makeText(KycActivity.this, "No Response From Server", Toast.LENGTH_LONG).show();
            }

        }



    }
    //************************************************************************************************

    private class KYC_Update extends AsyncTask<Void, Long, String> {


        @Override
        protected String doInBackground(Void... params) {
            try {
                org.apache.http.entity.mime.MultipartEntity entity = new MultipartEntity(
                        HttpMultipartMode.BROWSER_COMPATIBLE);

                String kyc_id=SharedHelper.getKey(KycActivity.this,"KYC_ID");
                String form_no=SharedHelper.getKey(KycActivity.this,"id");

                entity.addPart("id", new StringBody(kyc_id));
                entity.addPart("form_no", new StringBody(form_no));
                entity.addPart("reg_no", new StringBody(RegNo));
                entity.addPart("date_application", new StringBody(Datee));
                entity.addPart("provider_name", new StringBody(ProName));
                entity.addPart("father", new StringBody(FatherName));
                entity.addPart("mother", new StringBody(MotherName));
                entity.addPart("dob", new StringBody(Dob));
                entity.addPart("proof", new StringBody(Proof));
                entity.addPart("id_no", new StringBody(IdNo));
                entity.addPart("address", new StringBody(Address));
                entity.addPart("city", new StringBody(City));
                entity.addPart("dist", new StringBody(Distict));
                entity.addPart("pin", new StringBody(Pin));
                entity.addPart("present_address", new StringBody(PresentAddress));
                entity.addPart("city_1", new StringBody(City1));
                entity.addPart("dist_1", new StringBody(Distict1));
                entity.addPart("pin_1", new StringBody(Pin1));
                entity.addPart("service_name", new StringBody(ServiceName));
                entity.addPart("contact", new StringBody(Contact));
                entity.addPart("email", new StringBody(Email));
                entity.addPart("area", new StringBody(Area));
                entity.addPart("upto", new StringBody(Upto));
                entity.addPart("pincode", new StringBody(PinCode));
                entity.addPart("emplyee", new StringBody(Employee));
                entity.addPart("code", new StringBody(Code));
                entity.addPart("designation", new StringBody(Designation_A));
                entity.addPart("group", new StringBody(Group));
                entity.addPart("contact_1", new StringBody(Contact1));
                entity.addPart("adhar_no", new StringBody(Aadhar));
                entity.addPart("pan_card", new StringBody(Pan));
                entity.addPart("bank_name", new StringBody(Bank));
                entity.addPart("bank_ac_no", new StringBody(BankAc));
                entity.addPart("ifsc_code", new StringBody(Ifsc));
                entity.addPart("branch", new StringBody(Branch));

                entity.addPart("id_img", new FileBody(destination));
                // entity.addPart("id_img", new StringBody("logo.jpg"));


                Log.e("InputTo_KYC_API", "" + entity);


//***************************************************************
                String heder = SharedHelper.getKey(KycActivity.this, "access_token");
                Log.e("Authorization", "Bearer" + " " + heder);

                result = Utilities_Multipart.postEntityAndFindJson("http://erapidservice.com/public/api/provider/kyc_register_form_update", entity, heder);

                return result;

            } catch (Exception e) {
                // something went wrong. connection with the server error
                e.printStackTrace();
            }
            return null;
        }


        @Override
        protected void onPostExecute(String result) {
           // Toast.makeText(KycActivity.this, "res is "+result, Toast.LENGTH_LONG).show();

//            Log.e("result_", result);

            if (result != null) {
                Log.e("result", result);
                try {
                    JSONObject object = new JSONObject(result);
                   // String responce = object.getString("responce");
                    // String img = object.getString("img");
                    Toast.makeText(KycActivity.this, "Successful", Toast.LENGTH_LONG).show();

                    sessionwa.setLogin(true);
                    Intent resetIntent = new Intent(KycActivity.this, Home.class);
                    startActivity(resetIntent);
                    finish();


                } catch (JSONException e) {
                    //  dialog.dismiss();
                    Toast.makeText(KycActivity.this, e.toString(), Toast.LENGTH_SHORT).show();
                }

            } else {
                //dialog.dismiss();
                Toast.makeText(KycActivity.this, "No Response From Server", Toast.LENGTH_LONG).show();
            }

        }
    }
    //*************************************************************************
    }
