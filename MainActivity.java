public class MainActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private MenuItemAdapter adapter;
    private List<MenuItem> menuItems;        // Full list
    private List<MenuItem> displayedItems;   // Filtered list

    private EditText searchEditText;
    private Spinner filterSpinner;
    private Button clearFilterButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main); // Create this layout

        // Initialize views
        recyclerView = findViewById(R.id.recyclerView);
        searchEditText = findViewById(R.id.searchEditText);
        filterSpinner = findViewById(R.id.filterSpinner);
        clearFilterButton = findViewById(R.id.clearFilterButton);

        // Sample data
        menuItems = new ArrayList<>();
        menuItems.add(new MenuItem(1, "Caesar Salad", "Starter", 45.00));
        menuItems.add(new MenuItem(2, "Grilled Chicken", "Main Course", 120.00));
        menuItems.add(new MenuItem(3, "Chocolate Cake", "Dessert", 60.00));
        // Add more items as needed...

        displayedItems = new ArrayList<>(menuItems); // show all initially

        // Setup RecyclerView
        adapter = new MenuItemAdapter(displayedItems);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);

        // Setup filter spinner with options: "All", "Starter", "Main Course", "Dessert"
        ArrayAdapter<String> spinnerAdapter = new ArrayAdapter<>(this,
                android.R.layout.simple_spinner_item,
                new String[]{"All", "Starter", "Main Course", "Dessert"});
        spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        filterSpinner.setAdapter(spinnerAdapter);

        // Listeners
        filterSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                filterAndSearch();
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) { }
        });

        searchEditText.addTextChangedListener(new TextWatcher() {
            @Override public void beforeTextChanged(CharSequence s, int start, int count, int after) { }
            @Override public void onTextChanged(CharSequence s, int start, int before, int count) {
                filterAndSearch();
            }
            @Override public void afterTextChanged(Editable s) { }
        });

        clearFilterButton.setOnClickListener(v -> {
            searchEditText.setText("");
            filterSpinner.setSelection(0); // "All"
        });
    }

    private void filterAndSearch() {
        String searchText = searchEditText.getText().toString().toLowerCase().trim();
        String selectedCourse = filterSpinner.getSelectedItem().toString();

        displayedItems.clear();
        for (MenuItem item : menuItems) {
            boolean matchesSearch = item.getName().toLowerCase().contains(searchText);
            boolean matchesFilter = selectedCourse.equals("All") || item.getCourse().equals(selectedCourse);

            if (matchesSearch && matchesFilter) {
                displayedItems.add(item);
            }
        }
        adapter.notifyDataSetChanged();
    }
}
