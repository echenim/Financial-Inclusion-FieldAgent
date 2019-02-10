package k36tee.agent.Pages.Banks;

import android.app.DatePickerDialog;

import android.hardware.Camera;
import android.hardware.Camera.PictureCallback;
import android.hardware.Camera.ShutterCallback;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.AppCompatEditText;
import android.support.v7.widget.AppCompatTextView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.Toast;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;

import k36tee.agent.Dialogs.ItemSelectorDialog;
import k36tee.agent.FragmentPages;
import k36tee.agent.ProcessDialog;
import k36tee.agent.R;
import k36tee.agent.Request.AgentRequest;
import k36tee.agent.Utils;

/**
 * Created by myron echenim  on 8/23/16.
 */
public class AccountOpening extends FragmentPages implements SurfaceHolder.Callback {
    AppCompatTextView genderSelector;
    AppCompatTextView productSelector;
    AppCompatTextView dateSelector;


    Snackbar bar;
    Camera camera;
    SurfaceView surfaceView;
    SurfaceHolder surfaceHolder;

    PictureCallback rawCallback;
    ShutterCallback shutterCallback;
    PictureCallback jpegCallback;

    Button capture,startcam;


    String gender = null;
    String dateString = null;
    String product = null;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final View rootView = inflater.inflate(R.layout.account_opening, container, false);

        capture = (Button)rootView.findViewById(R.id.take_image_from_camera);
        startcam = (Button)rootView.findViewById(R.id.retake_image_from_camera);
        surfaceView = (SurfaceView)rootView.findViewById(R.id.camerapreview);
        surfaceHolder = surfaceView.getHolder();

        // Install a SurfaceHolder.Callback so we get notified when the
        // underlying surface is created and destroyed.
        surfaceHolder.addCallback(this);

        // deprecated setting, but required on Android versions prior to 3.0
        surfaceHolder.setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);

        capture.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                camera.takePicture(null, null, jpegCallback);
                camera.stopPreview();
                camera.release();
                camera = null;
            }
        });

        startcam.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                refreshCamera();
              //  camera.startFaceDetection();
            }
        });

        jpegCallback = new PictureCallback() {
            public void onPictureTaken(byte[] data, Camera camera) {
                FileOutputStream outStream = null;
                try {
                    outStream = new FileOutputStream(String.format("/sdcard/%d.jpg", System.currentTimeMillis()));
                    outStream.write(data);
                    outStream.close();
                    Log.d("Log", "onPictureTaken - wrote bytes: " + data.length);
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                } finally {
                }
                Toast.makeText(getContext(), "Picture Saved", Toast.LENGTH_LONG).show();
                refreshCamera();
            }
        };



        genderSelector = (AppCompatTextView) rootView.findViewById(R.id.gender_selector);
        genderSelector.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new ItemSelectorDialog() {
                    @Override
                    public void onListItemSelected(String requestObject) {
                        gender = requestObject;
                        genderSelector.setText(requestObject);
                    }
                }.fragmentInstanceDialogFragment(new ArrayList<String>(Arrays.asList(getResources().getStringArray(R.array.gender))), "Select Gender").show(getFragmentManager(), "");
            }
        });

        productSelector = (AppCompatTextView) rootView.findViewById(R.id.product);
        productSelector.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new ItemSelectorDialog() {
                    @Override
                    public void onListItemSelected(String requestObject) {
                        product = requestObject;
                        productSelector.setText(requestObject);
                    }
                }.fragmentInstanceDialogFragment(new ArrayList<String>(Arrays.asList(getResources().getStringArray(R.array.product))), "Select Gender").show(getFragmentManager(), "");
            }
        });
        dateSelector = (AppCompatTextView) rootView.findViewById(R.id.dob);
        dateSelector.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new DatePickerDialog(view.getContext(), new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker datePicker, int year, int month, int date) {
                        Calendar now = Calendar.getInstance();
                        if (year > now.get(Calendar.YEAR)) {
                            Utils.showMessage(getContext(), "invalid year is selected");
                        } else {
                            dateString = year+"-"+month+"-"+date;
                            dateSelector.setText(year+"-"+month+"-"+date);
                        }
                    }
                }, Calendar.getInstance()
                        .get(Calendar.YEAR), Calendar.getInstance().get(Calendar.MONTH),
                        Calendar.getInstance().get(Calendar.DAY_OF_MONTH)).show();
            }
        });

        rootView.findViewById(R.id.makeTransfer).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String phone = ((AppCompatEditText) rootView.findViewById(R.id.phoneField)).getText().toString().trim();
                String surName = ((AppCompatEditText) rootView.findViewById(R.id.surName)).getText().toString().trim();
                String firstName = ((AppCompatEditText) rootView.findViewById(R.id.fName)).getText().toString().trim();
                String otherName = ((AppCompatEditText) rootView.findViewById(R.id.oName)).getText().toString().trim();
                String pob = ((AppCompatEditText) rootView.findViewById(R.id.placeOfBirth)).getText().toString().trim();
                String address = ((AppCompatEditText) rootView.findViewById(R.id.address)).getText().toString().trim();
                String phoneNOK = ((AppCompatEditText) rootView.findViewById(R.id.phoneField_NOK)).getText().toString().trim();
                String nameNOK = ((AppCompatEditText) rootView.findViewById(R.id.name_NOK)).getText().toString().trim();
                String addressNOK = ((AppCompatEditText) rootView.findViewById(R.id.address_NOK)).getText().toString().trim();
                String cardNumber = ((AppCompatEditText) rootView.findViewById(R.id.cardIssuer)).getText().toString().trim();

                if (phone.length() >= 10) {

                    if (surName.isEmpty()) {
                        Utils.showMessage(getContext(), "surname can not be empty");
                    } else {
                        if (firstName.isEmpty()) {
                            Utils.showMessage(getContext(), "firstName can not be empty");
                        } else {
                            if (address.isEmpty()) {
                                Utils.showMessage(getContext(), "address can not be empty");
                            } else {
                                if (phoneNOK.length() >= 10) {
                                    if (nameNOK.isEmpty()) {
                                        Utils.showMessage(getContext(), "next if kin name can not be empty");
                                    } else {
                                        if (addressNOK.isEmpty()) {
                                            Utils.showMessage(getContext(), "next if kin address can not be empty");
                                        } else {

                                            if (pob.isEmpty()) {
                                                Utils.showMessage(getContext(), "place of birth field can not be empty");
                                            } else {
                                                if (gender == null) {
                                                    Utils.showMessage(getContext(), "select gender");
                                                } else {
                                                    if (dateString == null) {
                                                        Utils.showMessage(getContext(), "select date of birth");
                                                    } else {
                                                        if (product == null) {
                                                            Utils.showMessage(getContext(), "select product");
                                                        } else {
                                                           // http://192.52.243.153/platform/Banks/transaction/accountopening/{firstname}/{lastname}/{othername}/{gender}/{phonenumber}/{address}/{dateofbirth}/{placeofbirth}/{nextofkinname}/{nextofkinphone}/{nextofkinaddress}/{product}/{cardserialnumber}/{username}/{latitude}/{longitude}/
                                                         //   http://35.185.73.10/Agency/Banks/accountopening/Tosin/Rasak/Sulie/Female/07035547841/4 NIGER HOUSE/1988-04-04/BENIN/LYDIA/08035547841/ME/SAVINGS/012233333/wale.kuku@hotmail.com/6.1234/5.01234/

                                                            if (cardNumber.isEmpty()) {
                                                                Utils.showMessage(getContext(), "card number can not be empty");
                                                            } else {
                                                                processDialog.showDialog(getContext(), "opening account");
                                                                String details = "/" +firstName + "/" + surName + "/" + otherName + "/" + gender + "/" + phone + "/" + address + "/" + dateString + "/" + pob + "/" + nameNOK + "/" + phoneNOK + "/" + addressNOK + "/" + product + "/" + cardNumber;
                                                                new AgentRequest().new AgentTrasaction() {
                                                                    @Override
                                                                    protected void onRequestComplete(boolean status, String message) {
                                                                        processDialog.dismiss();
                                                                       // Toast.makeText(getActivity().getApplicationContext(),message,Toast.LENGTH_LONG).show();
                                                                        bar = Snackbar.make(rootView, message, Snackbar.LENGTH_INDEFINITE)
                                                                                .setAction("Dismiss", new View.OnClickListener() {
                                                                                    @Override
                                                                                     public void onClick(View v) {
                                                                                        bar.dismiss();
                                                                                    }
                                                                                });

                                                                        bar.show();

                                                                    }
                                                                }.acctOpening(getContext(), details);
                                                            }

                                                        }
                                                    }
                                                }
                                            }
//
                                        }
                                    }
                                } else {
                                    Utils.showMessage(getContext(), "phone number of next of kin is empty");
                                }
                            }
                        }
                    }
                } else {
                    Utils.showMessage(getContext(), "phone number is incorrect");
                }

            }
        });
        return rootView;
    }

    ProcessDialog processDialog = new ProcessDialog();

    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        try {
            // open the camera
            camera = Camera.open();
            //camera.startFaceDetection();
        } catch (RuntimeException e) {
            // check for exceptions
            System.err.println(e);
            return;
        }
        Camera.Parameters param;
        param = camera.getParameters();

        camera.setDisplayOrientation(90);

        // modify parameter
        param.setPreviewSize(352, 288);
        camera.setParameters(param);
        try {
            // The Surface has been created, now tell the camera where to draw
            // the preview.
            camera.setPreviewDisplay(surfaceHolder);
            camera.startPreview();
        } catch (Exception e) {
            // check for exceptions
            System.err.println(e);
            return;
        }
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
        refreshCamera();
        //camera.startFaceDetection();
    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        camera.stopPreview();
        camera.release();
        camera = null;
    }

    public void captureImage(View v) throws IOException {
        //take the picture
        camera.takePicture(null, null, jpegCallback);

    }

    public void refreshCamera() {
        if (surfaceHolder.getSurface() == null) {
            // preview surface does not exist
            return;
        }

        // stop preview before making changes
        try {
            camera.stopPreview();
        } catch (Exception e) {
            // ignore: tried to stop a non-existent preview
        }

        // set preview size and make any resize, rotate or
        // reformatting changes here
        // start preview with new settings
        try {
            camera.setPreviewDisplay(surfaceHolder);
            camera.startPreview();
        } catch (Exception e) {

        }
    }




}
