NOTATKA:

Dodanie:
  def room_version = "2.8.4"

    implementation "androidx.room:room-runtime:$room_version"
    annotationProcessor "androidx.room:room-compiler:$room_version"
do pliku build.gradle

1. klasa -> tabela (utworzenie klasy w projekcie która będzie "tabelą" w bazie,
   dodanie:
     @Entity(tableName = "tokareczki")
     @PrimaryKey(autoGenerate = true)
     @ColumnInfo(name = "srednica_toczenia")
     )
   
2. DAO interfejs (select insert update delete) - przykładowo:

   package com.example.room;

  import androidx.room.Dao;
  import androidx.room.Delete;
  import androidx.room.Insert;
  import androidx.room.Query;
  import androidx.room.Update;
  
  import java.util.List;
  
  @Dao
  public interface TokarkaDAO {
      @Insert
      void wstawTokarke(Tokarka tokarka);
      @Delete
      void usunTokarke(Tokarka tokarka);
      @Update
      void zmienParametryTokarki(Tokarka tokarka);
      @Query("SELECT * FROM tokareczki")
      List<Tokarka> zwrocWszystkieTokarki();
  
      @Query("SELECT model FROM tokareczki WHERE moc_silnika > :moc")
      List<String> zwrocNazwyTokarekOMocy(int moc);
  }
  
3. baza danych (utworzenie klasy w projekcie jako baza danych)
   
  @Database(entities = {Tokarka.class}, version = 1) nad klasą w pliku klasy
  public abstract class MaszynyDataBase extends RoomDatabase{ baza musi byc abstrakcyjna
  
    public abstract TokarkaDAO zwrocTokarkaDAO();
      private static MaszynyDataBase instancja;
      public static MaszynyDataBase zwrocBazeDanych(Context context){
          if (instancja == null){
              instancja = Room.databaseBuilder(
                      context.getApplicationContext(),
                      MaszynyDataBase.class,
                      "maszyny_db")
                      .allowMainThreadQueries()
                      .fallbackToDestructiveMigration()
                      .build();
          }
  
          return instancja;
      }
  }

  ------------------

   w MainActivity:

   MaszynyDataBase maszynyDB; - tworzymy baze danych

   w onCreate:
        maszynyDB = MaszynyDataBase.zwrocBazeDanych(MainActivity.this);
        maszynyDB.zwrocTokarkaDAO().wstawTokarke(new Tokarka("Nova", "Nebula", 35, 1500)); - wstawianie do bazy

   klikamy 3 kropki po boku ekranu i "App Inspection"

4. widok i dzialanie

   Przykladowa aplikacja w mainactivity z usuwaniem po kliknieciu i formularzem dodawania:

   public class MainActivity extends AppCompatActivity {

    MaszynyDataBase maszynyDB;
    ListView listView;
    ArrayAdapter<Tokarka> arrayAdapter;
    List<Tokarka> tokarkiList;
    EditText editTextNazwa;
    EditText editTextModel;
    EditText editTextSrednica;
    EditText editTextMoc;

    Button buttonDodaj;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        maszynyDB = MaszynyDataBase.zwrocBazeDanych(MainActivity.this);
        //maszynyDB.zwrocTokarkaDAO().wstawTokarke(new Tokarka("Nova", "Nebula", 35, 1500));
        //maszynyDB.zwrocTokarkaDAO().wstawTokarke(new Tokarka("DRHSelmeister", "StratosXL", 55, 2000));

        listView = findViewById(R.id.listView);
        tokarkiList = maszynyDB.zwrocTokarkaDAO().zwrocWszystkieTokarki();
        arrayAdapter = new ArrayAdapter<>(MainActivity.this, android.R.layout.simple_list_item_1, tokarkiList);
        listView.setAdapter(arrayAdapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                maszynyDB.zwrocTokarkaDAO().usunTokarke(tokarkiList.get(i));
                tokarkiList.remove(i);
                arrayAdapter.notifyDataSetChanged();
            }
        });

        editTextNazwa = findViewById(R.id.editTextNazwa);
        editTextModel = findViewById(R.id.editTextModel);
        editTextSrednica = findViewById(R.id.editTextSrednica);
        editTextMoc = findViewById(R.id.editTextMoc);
        buttonDodaj = findViewById(R.id.buttonDodaj);

        buttonDodaj.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String nazwa = editTextNazwa.getText().toString();
                String model = editTextModel.getText().toString();
                int srednica = Integer.parseInt(editTextSrednica.getText().toString());
                int moc = Integer.parseInt(editTextMoc.getText().toString());
                maszynyDB.zwrocTokarkaDAO().wstawTokarke(new Tokarka(nazwa, model, srednica, moc));
                tokarkiList.add(new Tokarka(nazwa, model, srednica, moc));
                arrayAdapter.notifyDataSetChanged();
            }
        });
    }
}
