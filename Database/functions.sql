-- Get fuel expenses for user
CREATE OR REPLACE FUNCTION get_fuel_expenses_for_user(ip_page_number IN int, ip_expenses_count IN int, ip_user_id IN varchar, op_all_rows OUT bigint, mcdCursor OUT refcursor) AS $$
BEGIN
	OPEN mcdCursor FOR
	SELECT fe.*
	FROM fuel_expenses fe
	ORDER BY fe.created_on desc
	LIMIT ip_expenses_count
	OFFSET (ip_page_number - 1) * ip_expenses_count;
	
	SELECT count(fe.fuel_expense_id)
	INTO op_all_rows
	FROM fuel_expenses fe
	JOIN cars c on fe.car_id = c.car_id
	JOIN users u on c.owner = u.user_id
	WHERE user_id = ip_user_id;
END;
$$ LANGUAGE plpgsql;

-- Get fuel expenses for user
CREATE OR REPLACE FUNCTION get_service_expenses_for_user(ip_page_number IN int, ip_expenses_count IN int, ip_user_id IN varchar, op_all_rows OUT bigint, mcdCursor OUT refcursor) AS $$
BEGIN
	OPEN mcdCursor FOR
	SELECT se.*, st.type_description
	FROM service_expenses se
	JOIN service_expense_types st ON se.type = st.type
	ORDER BY se.created_on desc
	LIMIT ip_expenses_count
	OFFSET (ip_page_number - 1) * ip_expenses_count;
	
	SELECT count(se.fuel_expense_id)
	INTO op_all_rows
	FROM service_expenses se
	JOIN cars c on se.car_id = c.car_id
	JOIN users u on c.owner = u.user_id
	WHERE user_id = ip_user_id;
END;
$$ LANGUAGE plpgsql;