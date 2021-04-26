package com.data.android.excelproject;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;

import android.annotation.SuppressLint;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.Timer;
import java.util.TimerTask;

public class timeTableActivity extends AppCompatActivity {

    TextView time1, time2, time3, time4, time5, time6;
    private int currentNotificationID = 0;
    private EditText etMainNotificationText, etMainNotificationTitle;
    private Button btnMainSendSimpleNotification, btnMainSendExpandLayoutNotification, btnMainSendNotificationActionBtn, btnMainSendMaxPriorityNotification, btnMainSendMinPriorityNotification, btnMainSendCombinedNotification, btnMainClearAllNotification;
    private NotificationManager notificationManager;
    private NotificationCompat.Builder notificationBuilder;
    private String notificationTitle;
    private String notificationText;
    private Bitmap icon;
    private int combinedNotificationCounter;
    long millis;

    String hr = "12";
    String min = "05";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_time_table);

        time1 = findViewById(R.id.time1);
        time2 = findViewById(R.id.time2);
        time3 = findViewById(R.id.time3);
        time4 = findViewById(R.id.time4);
        time5 = findViewById(R.id.time5);
        time6 = findViewById(R.id.time6);
//        getDate();
        Date currentTime = Calendar.getInstance().getTime();
        SimpleDateFormat df = new SimpleDateFormat("hh:mm a", Locale.getDefault());
        String formattedDate = df.format(currentTime);

        SimpleDateFormat df1 = new SimpleDateFormat("hh:mm a");
        Date d = null;
        try {
            d = df1.parse(formattedDate);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        Calendar cal = Calendar.getInstance();
        cal.setTime(d);
        cal.add(Calendar.MINUTE, 10);
        String newTime = df1.format(cal.getTime());
        Log.e("TAG", "onCreate: nmnhmnhmm " + newTime);

        time1.setText(newTime);

//        final String time = time1.getText().toString();



        final Handler handler = new Handler();
        Timer timer = new Timer();
        TimerTask doAsynchronousTask = new TimerTask() {
            @Override
            public void run() {
                handler.post(new Runnable() {
                    public void run() {
                        try {
                            _sendNotification("Attendence Application", "Reminder for upcoming lecture...");
                            Log.e("TAG", "run: ");
                            //your method here
                        } catch (Exception e) {

                        }
                    }
                });
            }
        };
        timer.schedule(doAsynchronousTask, getDate(formattedDate));


//        time1.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                changeTime("time1");
//            }
//        });
//
//        time2.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                changeTime("time2");
//            }
//        });
//
//        time3.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                changeTime("time3");
//            }
//        });
//        time4.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                changeTime("time4");
//            }
//        });
//        time5.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                changeTime("time5");
//            }
//        });
//        time6.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                changeTime("time6");
//            }
//        });
//        _sendNotification("Title", "Message", System.currentTimeMillis());

//        String oldDate = "12:52 AM";
////        Log.e("TAG", "notificationFunction: Data Time11 " + newTime);
//        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");
//        Date date = null;
//        try {
//            date = sdf.parse(oldDate);
//        } catch (ParseException e) {
//            e.printStackTrace();
//            Log.e("TAG", "Error :: " + e.toString());
//        }
//        Log.e("TAG", "onCreate:Milli Check25125:::: " + date);



//        _sendNotification("Title", "Message", millis);


    }

    private void changeTime(final String _time) {
        final EditText editText = new EditText(this);
        new AlertDialog.Builder(timeTableActivity.this)
                .setTitle("Change Lecture Time")
                .setNegativeButton(android.R.string.no, null)
                .setView(editText)
                .setPositiveButton("Change", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface arg0, int arg1) {
                        try {
                            if (editText.getText().length() > 0) {
                                notificationFunction(editText.getText().toString());
                                Log.e("TAG", "onClick: Entry :: " + editText.getText().toString());
                                switch (_time) {
                                    case "time1":
                                        time1.setText(editText.getText().toString());
                                        break;
                                    case "time2":
                                        time2.setText(editText.getText().toString());
                                        break;
                                    case "time3":
                                        time3.setText(editText.getText().toString());
                                        break;
                                    case "time4":
                                        time4.setText(editText.getText().toString());
                                        break;
                                    case "time5":
                                        time5.setText(editText.getText().toString());
                                        break;
                                    case "time6":
                                        time6.setText(editText.getText().toString());
                                        break;
                                }
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }

                        ((ViewGroup) editText.getParent()).removeView(editText);
                    }
                }).create().show();


    }

    private void notificationFunction(String userTime) {
        Date c = Calendar.getInstance().getTime();
        SimpleDateFormat df = new SimpleDateFormat("yyyy/MM/dd", Locale.getDefault());
        String formattedDate = df.format(c);
        Log.e("TAG", "notificationFunction: test " + formattedDate);
        Log.e("TAG", "notificationFunction: test Time " + userTime);

        //..........................................................................

//        String myTime = userTime;
        SimpleDateFormat df1 = new SimpleDateFormat("HH:mm a");
        Date d = null;
        try {
            d = df1.parse(userTime);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        Calendar cal = Calendar.getInstance();
        cal.setTime(d);
        cal.add(Calendar.MINUTE, 5);
        String newTime = df1.format(cal.getTime());
        Log.e("TAG", "onCreate: " + newTime);
//
        String myDate = formattedDate + " " + newTime;
        String oldDate = "12:52 AM";
        Log.e("TAG", "notificationFunction: Data Time11 " + newTime);
        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");
        Date date = null;
        try {
            date = sdf.parse(oldDate);
        } catch (ParseException e) {
            e.printStackTrace();
            Log.e("TAG", "Error :: " + e.toString());
        }
        Log.e("TAG", "onCreate:Milli Old:: " + date.getTime());
        try {
            date = sdf.parse(myDate);
        } catch (ParseException e) {
            e.printStackTrace();
            Log.e("TAG", "Error :: " + e.toString());
        }
        millis = date.getTime();
        Log.e("TAG", "onCreate:Milli new :: " + millis);
        _sendNotification("Attendence Application", "Reminder for upcomming lecture...");

    }

    private void _sendNotification(String messageTitle, String messageBody) {
        NotificationManager notificationManager =
                (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        Intent intent = new Intent(this, timeTableActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_SINGLE_TOP);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_ONE_SHOT);
        Uri soundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);

        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            @SuppressLint("WrongConstant")
            NotificationChannel notificationChannel = new NotificationChannel("my_notification", "n_channel", NotificationManager.IMPORTANCE_MAX);
            notificationChannel.setDescription("description");
            notificationChannel.setName("Channel Name");
            assert notificationManager != null;
            notificationManager.createNotificationChannel(notificationChannel);
        }

        NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(this)
                .setSmallIcon(R.mipmap.ic_launcher_foreground)
                .setLargeIcon(BitmapFactory.decodeResource(getResources(), R.mipmap.ic_launcher_foreground))
                .setContentTitle(messageTitle)
                .setContentText(messageBody)
                .setAutoCancel(true)
                .setSound(soundUri)
                .setContentIntent(pendingIntent)
                .setDefaults(Notification.DEFAULT_ALL)
                .setOnlyAlertOnce(true)
                .setChannelId("my_notification")
                .setColor(Color.parseColor("#3F5996"));

        //.setProgress(100,50,false);
        assert notificationManager != null;
        int m = (int) ((new Date().getTime() / 1000L) % Integer.MAX_VALUE);
        notificationManager.notify(m, notificationBuilder.build());
    }
    private Date getDate(String s) {
        Log.e("TAG", "getDate: Time .." + s);
        try {
            final SimpleDateFormat sdf = new SimpleDateFormat("hh:mm a");
            final Date dateObj = sdf.parse(s);

            hr =    new SimpleDateFormat("HH").format(dateObj);
            min =   new SimpleDateFormat("mm").format(dateObj);

            Log.e("TAG", "onCreate: Neewflfl " + new SimpleDateFormat("HH:mm").format(dateObj) + "  " + hr + "  " + min);

            System.out.println(dateObj);
            System.out.println(new SimpleDateFormat("HH:mm").format(dateObj));
            Log.e("TAG", "onCreate: Neewflfl " + new SimpleDateFormat("HH:mm").format(dateObj));
        } catch (final ParseException e) {
            e.printStackTrace();
            Log.e("TAG", "onCreate: " + e.toString());
        }

        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.MINUTE, 5);
        cal.set(Calendar.HOUR_OF_DAY, Integer.parseInt(hr));
        cal.set(Calendar.MINUTE, Integer.parseInt(min));
        cal.set(Calendar.SECOND, 0);
        Log.e("TAG", "getDate: " + cal.getTime().toString());
        return cal.getTime();


    }

}