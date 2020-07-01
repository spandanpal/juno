package com.juno

import android.app.Dialog
import android.content.Context
import android.view.Window

class QCProgressDialog {



    /**
     * @param ctx Context
     * @param msg message
     */
    companion object {
        @JvmStatic
        private var progressDialog: Dialog? = null
        var isProgressDialogShown = false
        fun showProgressDialog(ctx: Context?, msg: String?) {
            try {
                progressDialog = Dialog(ctx!!)
                // no tile for the dialog
                progressDialog!!.requestWindowFeature(Window.FEATURE_NO_TITLE)
                progressDialog!!.setContentView(R.layout.activity_test)
                progressDialog!!.setTitle(msg)
                // you can change or add this line according to your need
                progressDialog!!.setCancelable(false)
                progressDialog!!.setCanceledOnTouchOutside(false)

                /* VideoView videoView = progressDialog.findViewById(R.id.videoView);

                Uri uri = Uri.parse("android.resource://" + ctx.getPackageName() + "/" + R.raw.loader);
                videoView.setVideoURI(uri);
                videoView.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
                    @Override
                    public void onPrepared(MediaPlayer mp) {
                        mp.setLooping(true);
                        videoView.start();
                    }
                });*/progressDialog!!.show()
                isProgressDialogShown = true
            } catch (e: Exception) {
                isProgressDialogShown = false
                e.printStackTrace()
            }
        }

        fun hideProgressDialog() {
            try {
                if (progressDialog != null) {
                    progressDialog!!.dismiss()
                    isProgressDialogShown = false
                }
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }

    }

    /**
     * Method to hide the dialog.
     */

}