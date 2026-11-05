public class MenuItemActivity extends AppCompatActivity {

    private EditText nameEditText, priceEditText;
    private Spinner courseSpinner;
    private Button saveButton;

    private MenuItem editingItem = null; // null if adding new item

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_item); // Create this layout

        nameEditText = findViewById(R.id.nameEditText);
        priceEditText = findViewById(R.id.priceEditText);
        courseSpinner = findViewById(R.id.courseSpinner);
        saveButton = findViewById(R.id.saveButton);

        // Setup spinner values
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this,
                android.R.layout.simple_spinner_item,
                new String[]{"Starter", "Main Course", "Dessert"});
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        courseSpinner.setAdapter(adapter);

        // Check if editing an existing item
        Intent intent = getIntent();
        if (intent.hasExtra("menuItem")) {
            editingItem = (MenuItem) intent.getSerializableExtra("menuItem");
            nameEditText.setText(editingItem.getName());
            priceEditText.setText(String.valueOf(editingItem.getPrice()));
            int spinnerPosition = adapter.getPosition(editingItem.getCourse());
            courseSpinner.setSelection(spinnerPosition);
        }

        saveButton.setOnClickListener(v -> {
            if (validateInput()) {
                String name = nameEditText.getText().toString().trim();
                String course = courseSpinner.getSelectedItem().toString();
                double price = Double.parseDouble(priceEditText.getText().toString().trim());

                if (editingItem != null) {
                    // Update existing item
                    editingItem.setName(name);
                    editingItem.setCourse(course);
                    editingItem.setPrice(price);
                } else {
                    // Create new item
                    editingItem = new MenuItem(generateId(), name, course, price);
                }

                // Return the result to MainActivity
                Intent resultIntent = new Intent();
                resultIntent.putExtra("menuItem", editingItem);
                setResult(RESULT_OK, resultIntent);
                finish();
            }
        });
    }

    private boolean validateInput() {
        if (nameEditText.getText().toString().trim().isEmpty()) {
            nameEditText.setError("Name is required");
            return false;
        }

        String priceStr = priceEditText.getText().toString().trim();
        if (priceStr.isEmpty()) {
            priceEditText.setError("Price is required");
            return false;
        }

        try {
            double price = Double.parseDouble(priceStr);
            if (price < 0) {
                priceEditText.setError("Price cannot be negative");
                return false;
            }
        } catch (NumberFormatException e) {
            priceEditText.setError("Invalid price");
            return false;
        }

        return true;
    }

    private int generateId() {
        // Simple ID generator using timestamp
        return (int) (System.currentTimeMillis() % Integer.MAX_VALUE);
    }
}
