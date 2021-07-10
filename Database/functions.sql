-- Get fuel expenses for user
CREATE OR REPLACE FUNCTION get_fuel_expenses_for_user(ip_page_number IN int, ip_expenses_count IN int, ip_user_id IN varchar, op_all_rows OUT bigint, mcdCursor OUT refcursor) AS $$
BEGIN
	OPEN mcdCursor FOR
	SELECT fe.*
	FROM fuel_expenses fe
	JOIN cars c ON fe.car_id = c.car_id
	JOIN users u ON c.owner = u.user_id
	WHERE u.user_id = ip_user_id
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
	JOIN cars c on se.car_id = c.car_id
	JOIN users u on c.owner = u.user_id
	WHERE user_id = ip_user_id
	ORDER BY se.created_on desc
	LIMIT ip_expenses_count
	OFFSET (ip_page_number - 1) * ip_expenses_count;
	
	SELECT count(se.service_expense_id)
	INTO op_all_rows
	FROM service_expenses se
	JOIN cars c on se.car_id = c.car_id
	JOIN users u on c.owner = u.user_id
	WHERE user_id = ip_user_id;
END;
$$ LANGUAGE plpgsql;

-- Get expenses summary
CREATE OR REPLACE FUNCTION get_expenses_summary(ip_user_id IN varchar, ip_car_id IN varchar, ip_year IN int, mcdFuelCursor OUT refcursor, mcdServiceCursor OUT refcursor) AS $$
DECLARE
	v_fuel_total numeric;
	v_sql_fuel varchar;
	v_where_fuel varchar;
	v_sql_service varchar;
	v_where_service varchar;
BEGIN
	--v_sql := 'SELECT sum(price
	OPEN mcdFuelCursor FOR
	SELECT date_part('year', fe.created_on) AS year, date_part('month', fe.created_on) AS month, sum((fe.price_per_litre * fe.litres) - COALESCE(fe.discount, 0)) AS total
	FROM fuel_expenses fe
	JOIN cars c ON fe.car_id = c.car_id
	WHERE date_part('year', fe.created_on) = COALESCE(ip_year, date_part('year', NOW()))
	AND fe.car_id = COALESCE(ip_car_id, fe.car_id)
	AND c.owner = ip_user_id
	GROUP BY date_part('year', fe.created_on), date_part('month', fe.created_on)
	ORDER BY year, month;
	
	OPEN mcdServiceCursor FOR
	SELECT date_part('year', se.created_on) AS year, date_part('month', se.created_on) AS month, sum(se.price) AS total
	FROM service_expenses se
	JOIN cars c ON se.car_id = c.car_id
	WHERE date_part('year', se.created_on) = COALESCE(ip_year, date_part('year', NOW()))
	AND se.car_id = COALESCE(ip_car_id, se.car_id)
	AND c.owner = ip_user_id
	GROUP BY date_part('year', se.created_on), date_part('month', se.created_on)
	ORDER BY year, month;
END;
$$ LANGUAGE plpgsql;

-- Get user policies by criteria
CREATE OR REPLACE FUNCTION get_user_policies_by_criteria(ip_type IN int, ip_status IN int, ip_car_id IN varchar, ip_user_id IN varchar, mcdCursor OUT refcursor) AS $$
DECLARE
	v_now timestamp;
BEGIN
	v_now := NOW();
	IF ip_status = 0 THEN
		-- status 0  -> active
		OPEN mcdCursor FOR
		SELECT p.*
		FROM policies p
		JOIN cars c ON p.car_id = c.car_id
		WHERE v_now >= p.start_date
		AND v_now <= p.end_date
		AND p.type = COALESCE(ip_type, p.type)
		AND c.owner = ip_user_id
		AND c.car_id = COALESCE(ip_car_id, c.car_id)
		ORDER BY p.end_date ASC, p.start_date DESC;
	ELSIF ip_status = 1 THEN
		-- status 1 -> expired
		OPEN mcdCursor FOR
		SELECT p.*
		FROM policies p
		JOIN cars c ON p.car_id = c.car_id
		WHERE v_now > p.end_date
		AND p.type = COALESCE(ip_type, p.type)
		AND c.owner = ip_user_id
		AND c.car_id = COALESCE(ip_car_id, c.car_id)
		ORDER BY p.end_date ASC, p.start_date DESC;
	ELSIF ip_status = 2 THEN
		-- status 2 -> pending
		OPEN mcdCursor FOR
		SELECT p.*
		FROM policies p
		JOIN cars c ON p.car_id = c.car_id
		WHERE v_now <= p.start_date
		AND p.type = COALESCE(ip_type, p.type)
		AND c.owner = ip_user_id
		AND c.car_id = COALESCE(ip_car_id, c.car_id)
		ORDER BY p.end_date ASC, p.start_date DESC;
	ELSE
		-- status -1 -> all
		OPEN mcdCursor FOR
		SELECT p.*
		FROM policies p
		JOIN cars c ON p.car_id = c.car_id
		WHERE p.type = COALESCE(ip_type, p.type)
		AND c.owner = ip_user_id
		AND c.car_id = COALESCE(ip_car_id, c.car_id)
		ORDER BY p.end_date ASC, p.start_date DESC;
	END IF;
END;
$$ LANGUAGE plpgsql;