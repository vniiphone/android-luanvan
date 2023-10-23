package appdulich.booking.giaodien;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import java.util.ArrayList;
import java.util.List;

import appdulich.booking.model.Profile;
import appdulich.booking.model.SessionManager;
import appdulich.booking.model.api_interface.ProfileUserAPI;
import appdulich.booking.retrofit.RetrofitService;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import thud.helloworld.R;

public class ListProfileActivity extends AppCompatActivity {
    final static String TAG = "ListProfileActivity";
    RecyclerView recycleview;
    SwipeRefreshLayout swipeRefreshLayout;
    public static ArrayList<Profile> profilesListArray;
    private String token = "";
    private long user_id = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_profile);

        SessionManager sessionManager = new SessionManager(this);
        token = sessionManager.getToken();
        user_id = sessionManager.getUserId();
        Log.d(TAG, "Token: " + token);

        profilesListArray = new ArrayList<>(); // Khởi tạo List Tour

        anhXaId();
        listener();
        loadProfileList();
    }

    @Override
    protected void onResume() {
        Log.d(TAG, "onResume");
        super.onResume();
    }

    @Override
    protected void onRestart() {
        loadProfileList();
        super.onRestart();
    }

    public void anhXaId() {
        swipeRefreshLayout = findViewById(R.id.swipeRefreshLayout_list_profile);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        setTitle("Danh Sách Profile");
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        recycleview = findViewById(R.id.recyclerview_profile_list);
        recycleview.setLayoutManager(new LinearLayoutManager(this));

    }

    public void listener() {
        swipeRefreshLayout.setOnRefreshListener(() -> {
            swipeRefreshLayout.setRefreshing(false);
            loadProfileList();
        });
    }

    private void loadProfileList() {
        RetrofitService retrofitService = new RetrofitService(this);
        String newToken = token;
        retrofitService.updateToken(newToken);
        Retrofit retrofit = retrofitService.getRetrofit();
        ProfileUserAPI api = retrofit.create(ProfileUserAPI.class);
        api.getAllProfiles(user_id).enqueue(new Callback<List<Profile>>() {
            @Override
            public void onResponse(Call<List<Profile>> call, Response<List<Profile>> response) {
                if (response.isSuccessful()) {
                    Log.d(TAG, "Success to get profile: " + response.body());
                    populateListView(response.body());
                } else {
                    Log.d(TAG, "Failed to get profile: " + response.message());
                }
            }

            @Override
            public void onFailure(Call<List<Profile>> call, Throwable t) {
                Log.d(TAG, "Failed to get profile: " + t.getMessage());
            }
        });
    }

    private void populateListView(List<Profile> profiles) {
        ListProfileAdapter listProfileAdapter = new ListProfileAdapter(getApplicationContext(), profiles);
        recycleview.setAdapter(listProfileAdapter);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.mnu_back) {
            // Trở về trang trước
            finish();
            return true; // Trả về true để chỉ ra rằng bạn đã xử lý sự kiện này.
        } else if (id == R.id.mnu_add_profile) {
            // Gắn cờ vào Intent để xác định trạng thái add Profile
            Intent intent = new Intent(ListProfileActivity.this, ProfileActivity.class);
            intent.putExtra("isAddingProfile", true);
            startActivity(intent);
            return true;// Trả về true để chỉ ra rằng bạn đã xử lý sự kiện này.
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_on_list_profile, menu);
        return super.onCreateOptionsMenu(menu);
    }
}


class ListProfileAdapter extends RecyclerView.Adapter<ListProfileAdapter.ViewHolder> {

    private Context context;
    private List<Profile> mProfile = new ArrayList<>();

    public ListProfileAdapter(Context context, List<Profile> mProfile) {
        if (!mProfile.isEmpty()) {
            this.context = context;
            this.mProfile = mProfile;
            notifyDataSetChanged();
        } else {
            this.context = context;
        }
    }


    public ListProfileAdapter() {

    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_profile, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        final Profile profile = mProfile.get(position);

        holder.tv_id.setText(String.valueOf(profile.getId()));
        holder.tv_fullName.setText(profile.getLastName() + " " + profile.getName());
        holder.tv_phone.setText(profile.getPhoneNumber());

        holder.linearLayout.setOnClickListener(view -> {
            onClickGoToDetailProfile(profile);
        });

        holder.img_edit.setOnClickListener(view -> {
            onClickGoToEditDetailProfile(profile);
        });
    }

    private void onClickGoToEditDetailProfile(Profile profile) {
        Intent intent = new Intent(context, ProfileActivity.class);
        intent.putExtra("isEditingProfile", true);
        Bundle bundle = new Bundle();
        bundle.putLong("profile_id", profile.getId());
        bundle.putSerializable("object_profile", profile);
        intent.putExtras(bundle);
        // Thêm FLAG_ACTIVITY_NEW_TASK trước khi mở Activity
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(intent);
    }


    private void onClickGoToDetailProfile(Profile profile) {
        Intent intent = new Intent(context, ProfileActivity.class);
        Bundle bundle = new Bundle();
        bundle.putSerializable("object_profile", profile);
        bundle.putLong("profile_id", profile.getId());
        Log.d("onClickGoToDetailProfile", "onClickGoToDetailProfile id: " + profile.getId());
        intent.putExtras(bundle);
        // Thêm FLAG_ACTIVITY_NEW_TASK trước khi mở Activity
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(intent);
    }

    @Override
    public int getItemCount() {
        return mProfile.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView tv_fullName, tv_phone, tv_id;
        LinearLayout linearLayout;
        ImageView img_edit, img_delete;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            tv_id = itemView.findViewById(R.id.item_profile_id);
            tv_fullName = itemView.findViewById(R.id.item_profile_name_lastname);
            tv_phone = itemView.findViewById(R.id.item_profile_name_phoneNumber);
            linearLayout = itemView.findViewById(R.id.linear_profile_item);
            img_edit = itemView.findViewById(R.id.img_edting_profile);
            img_delete = itemView.findViewById(R.id.img_detele_profile);
        }
    }
}