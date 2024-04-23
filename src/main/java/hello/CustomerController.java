package hello;

import java.util.List;
import org.springframework.http.ResponseEntity;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/customers")
public class CustomerController {
    private JdbcTemplate jdbcTemplate;

    public CustomerController(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @PostMapping
    public ResponseEntity<Void> save(@RequestBody Customer customer) {
        String sql = "INSERT INTO customers(first_name, last_name) VALUES (?,?)";
        jdbcTemplate.update(sql, customer.getFirstName(), customer.getLastName());
        return ResponseEntity.ok().build();
    }

    @GetMapping
    public ResponseEntity<List<Customer>> list() {
        String sql = "select id, first_name, last_name from customers";
        List<Customer> customers = jdbcTemplate.query(
                sql,
                (resultSet, rowNum) -> new Customer(
                        resultSet.getLong("id"),
                        resultSet.getString("first_name"),
                        resultSet.getString("last_name"))
        );
        return ResponseEntity.ok().body(customers);
    }
}
