package be.kuleuven.signindemo

import android.app.Activity
import android.content.Intent
import android.view.View
import androidx.activity.result.ActivityResult
import androidx.appcompat.app.AppCompatActivity
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.android.material.snackbar.Snackbar

// https://stackoverflow.com/questions/47437678/why-do-i-get-com-google-android-gms-common-api-apiexception-10
fun ApiException.developerError(): Boolean = this.statusCode == 10
// https://stackoverflow.com/questions/47632035/google-sign-in-error-12500
fun ApiException.googleLoginError(): Boolean = this.statusCode == 12500

/**
 * Use Google's Play Service to log in a user. Requires the following dependencies:
 * implementation("com.google.firebase:firebase-bom:28.3.1")
 * implementation("com.google.android.gms:play-services-auth:19.2.0")
 * implementation("com.google.android.gms:play-services-base:17.6.0")
 */
class GoogleSignInClient {

    fun tryGoogleSignedIn(result: ActivityResult, view: View): GoogleSignInAccount? {
        if(result.resultCode != AppCompatActivity.RESULT_OK) {
            Snackbar.make(view, "Unable to login using Google Sign In", Snackbar.LENGTH_LONG).show()
            return null
        }

        val completedSignIn = GoogleSignIn.getSignedInAccountFromIntent(result.data)

        try {
            val account = completedSignIn.getResult(ApiException::class.java)
            Snackbar.make(view, "Welcome, ${account.displayName}!", Snackbar.LENGTH_SHORT).show()

            return account
        } catch (apiEx: ApiException) {
            when {
                apiEx.developerError() -> Snackbar.make(view, "GAPI Developer Error, wrong keys?", Snackbar.LENGTH_LONG).show()
                apiEx.googleLoginError() -> Snackbar.make(view, "GAPI Play Login Error, Firebase setup OK?", Snackbar.LENGTH_LONG).show()
                else -> Snackbar.make(view, "GAPI Error #${apiEx.statusCode}: ${apiEx.message}", Snackbar.LENGTH_LONG).show()
            }
        }
        return null
    }

    fun createSignInIntentFor(activity: Activity): Intent {
        // The "New" way to login using Google Services, instead of AuthUtil
        // See https://developers.google.com/identity/sign-in/android/migration-guide
        val signInOpts = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken("myuniqueid.apps.googleusercontent.com")
            .requestEmail()
            .build()
        val client = GoogleSignIn.getClient(activity, signInOpts)
        return client.signInIntent
    }
}