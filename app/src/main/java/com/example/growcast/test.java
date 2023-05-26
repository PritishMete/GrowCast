//import android.os.Bundle;
//import android.view.View;
//import android.widget.Button;
//import android.widget.EditText;
//
//import androidx.appcompat.app.AppCompatActivity;
//
//public class Signup extends AppCompatActivity {
//    private EditText name;
//    private Button signupButton;
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_signup);
//
//        name = findViewById(R.id.nameEditText);
//        signupButton = findViewById(R.id.signupButton);
//
//        signupButton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                String userName = name.getText().toString();
//                User.setUserName(userName); // Set the userName in User class
//                // Start User activity
//                startActivity(new Intent(Signup.this, User.class));
//            }
//        });
//    }
//}
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//import android.os.Bundle;
//        import android.widget.TextView;
//
//        import androidx.appcompat.app.AppCompatActivity;
//
//public class User extends AppCompatActivity {
//    private static String userName;
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_user);
//
//        TextView userNameTextView = findViewById(R.id.userNameTextView);
//        userNameTextView.setText(userName); // Set the userName in TextView
//    }
//
//    public static void setUserName(String name) {
//        userName = name;
//    }
//}
