create or replace package proj2 as
	
	type ref_cs_emp is ref cursor;
	type ref_cs_cus is ref cursor;
	type ref_cs_dis is ref cursor;
	type ref_cs_pro is ref cursor;
	type ref_cs_pur is ref cursor;
	type ref_cs_sup is ref cursor;
	type ref_cs_su is ref cursor;
	type ref_cs_log is ref cursor;
    type ref_cs_pur_saving is ref cursor;
    type ref_cs_monthSale is ref cursor;
    /*use for add purchases, as return value*/
    addValid number := 1; 
   

	function show_employees
	return ref_cs_emp;

	function show_customers
        return ref_cs_cus;

	function show_discounts
        return ref_cs_dis;

	function show_products
        return ref_cs_pro;

	function show_purchases
        return ref_cs_pur;

	function show_suppliers
        return ref_cs_sup;

	function show_supplies
        return ref_cs_su;

	function show_logs
        return ref_cs_log;

    function purchase_saving(inputPur in purchases.pur#%type)
    return number;
        
    procedure monthly_sale_activities(
        employee_id in employees.eid%type,
        rst out ref_cs_monthSale);

    procedure add_customer(c_id in customers.cid%type, 
        c_name in customers.name%type,
        c_telephone# in customers.telephone#%type);


    procedure add_purchase(e_id in purchases.eid%type,
        p_id in purchases.pid%type,
        c_id in purchases.cid%type,
        pur_qty in purchases.qty%type,
        addValid out number,
        new_qoh out number);

    procedure delete_purchase(p_pur# in purchases.pur#%type);
       
end;
/
show errors


create or replace package body proj2 as

	function show_employees
	return ref_cs_emp is
	emp ref_cs_emp;
	begin
		open emp for
		select * 
        from employees
        order by eid asc;
		return emp;
	end;

	function show_customers
        return ref_cs_cus is
        cus ref_cs_cus;
        begin
                open cus for
                select cid,name,telephone#,visits_made,to_char(last_visit_date,'DD-MON-YYYY') 
                from customers
                order by cid asc;
                return cus;
        end;

	function show_discounts
        return ref_cs_dis is
        dis ref_cs_dis;
        begin
                open dis for
                select discnt_category, to_number(discnt_rate, '9.99') 
                from discounts
                order by discnt_category asc;
                return dis;
        end;

	function show_products
        return ref_cs_pro is
        pro ref_cs_pro;
        begin
                open pro for
                select * 
                from products
                order by pid asc;
                return pro;
        end;

	function show_purchases
        return ref_cs_pur is
        pur ref_cs_pur;
        begin
                open pur for
                select pur#,eid,pid,cid,qty,to_char(ptime,'DD-MON-YYYY HH24:MI:SS') as ptime, total_price 
                from purchases
                order by pur# asc;
                return pur;
        end;

	function show_suppliers
        return ref_cs_sup is
        sup ref_cs_sup;
        begin
                open sup for
                select * 
                from suppliers
                order by sid asc;
                return sup;
        end;

	function show_supplies
        return ref_cs_su is
        su ref_cs_su;
        begin
                open su for
                select sup#,pid,sid,to_char(sdate,'DD-MON-YY'),quantity 
                from supplies
                order by sup# asc;
                return su;
        end;

	function show_logs
        return ref_cs_log is
        log ref_cs_log;
        begin
                open log for
                select * 
                from logs
                order by log# asc;
                return log;
        end;

    function purchase_saving(inputPur in purchases.pur#%type)
        return number
        is
        pur_saving number := 0;
        p_qty purchases.qty%type := 0;
        p_price products.original_price%type := 0;
        p_pid purchases.pid%type := null;
        p_total_price purchases.total_price%type := 0;

        begin
                select qty into p_qty
                from purchases
                where pur# = inputPur;
              
                select pid into p_pid
                from purchases
                where pur# = inputPur;

                select total_price into p_total_price
                from purchases
                where pur# = inputPur;

                select original_price into p_price
                from products
                where pid = p_pid;
                pur_saving := p_qty * p_price - p_total_price;
                return pur_saving;        
--
        exception 
                when no_data_found then
                raise_application_error(-20005, 'User-Defined Exceptions: PUR# is not found.');
                when others then
                raise_application_error(-20010, 'User-Defined Exceptions: PUR# format error. PUR# only contain numbers 0~999999.');

--
        end;
 
        procedure monthly_sale_activities(
                employee_id in employees.eid%type,
                rst out ref_cs_monthSale)
        as

        begin
                open rst for
                /*figure out the output order*/
                        select name, ceid, sqty, tprice, t
                        from(

                        (
                                select to_char(ptime,'MON/YYYY') as t, sum(qty) as sqty
                                from employees natural join purchases
                                where eid = employee_id
                                group by to_char(ptime, 'MON/YYYY')
                        )
                        natural join
                        (
                                select to_char(ptime,'MON/YYYY') as t, sum(total_price) as tprice
                                from employees natural join purchases
                                where eid = employee_id
                                group by to_char(ptime, 'MON/YYYY')
                        )
                        natural join
                        (
                                select to_char(ptime,'MON/YYYY') as t, count(eid) as ceid
                                from employees natural join purchases
                                where eid = employee_id
                                group by to_char(ptime, 'MON/YYYY')
                        )), 
                        (select name from employees where eid = employee_id) 
                        order by t desc
                        ;  
-- 
        exception 
                when no_data_found then
                raise_application_error(-20006, 'User-Defined Exceptions: EID is not found.');
                when others then
                raise_application_error(-20009, 'User-Defined Exceptions: Only input right format EID(exx) can get the right search value');
        end;
--



        procedure add_customer(c_id in customers.cid%type, 
                c_name in customers.name%type,
                c_telephone# in customers.telephone#%type)
        as
        begin
            insert into customers(cid,name,telephone#,last_visit_date) values (c_id,c_name,c_telephone#,sysdate);
--
        exception 
            when DUP_VAL_ON_INDEX then
                raise_application_error(-20007, 'User-Defined Exceptions: CID input duplication error. CID: cxxx');
            when others then
                raise_application_error(-20008, 'User-Defined Exceptions: CID/NAME/TELEPHONE# input format error. CID: cxxx/ NAME: less than 12 char/ TEL#: xxx-xxx-xxxx ');     
-- 
        end;


        procedure add_purchase(e_id in purchases.eid%type,
                p_id in purchases.pid%type,
                c_id in purchases.cid%type,
                pur_qty in purchases.qty%type,
                addValid out number,
                new_qoh out number)
        as
        bufferTotalPrice number := 0;
        bufferDiscount number :=0;
        bufferPrice number :=0;
        bufferQoh number :=0;
        bufferQohNew number :=0;
        bufferQohSh number :=0;
        s_id char(2);

        begin
            select qoh into bufferQoh
            from products
            where pid = p_id;

            select original_price into bufferPrice
            from products
            where pid = p_id;

            select qoh_threshold into bufferQohSh
            from products
            where pid = p_id;

            select discnt_rate into bufferDiscount 
            from products natural join discounts
            where pid = p_id;
/*addValid = 0: invalid purchase; =1:valid purchase and no need supply order*/
/*=2: valid purchase and need supply order*/
            if (pur_qty > bufferQoh) then
                addValid := 0;
            else
                bufferTotalPrice := pur_qty * (1 - bufferDiscount) * bufferPrice;
                insert into purchases(eid,pid,cid,qty,ptime,total_price) values
                   (e_id,p_id,c_id,pur_qty,sysdate,bufferTotalPrice);
                addValid := 1;
                
                select qoh into bufferQohNew
                from products
                where pid = p_id;
                if(bufferQoh - pur_qty != bufferQohNew) then
                    addValid := 2;
                end if;
                new_qoh := bufferQohNew;
            end if;

        exception
            when no_data_found then
                raise_application_error(-20015, 'User-Defined Exceptions: EID/CID/PID should exist in related table and format should be correct. EID:exx/ CID:cxxx/ PID:pxxx ');
            when others then
                raise_application_error(-20015, 'User-Defined Exceptions: EID/CID/PID should exist in related table and format should be correct. EID:exx/ CID:cxxx/ PID:pxxx ');
        end;


        procedure delete_purchase(p_pur# in purchases.pur#%type)
        as
        buffer purchases.qty%type;
        begin
        /*check out if the input p_pur# is already exist in purchases table*/
            select qty into buffer
            from purchases
            where pur# = p_pur#;

            delete 
            from purchases
            where pur# = p_pur#;
--           
        exception
            when no_data_found then
                raise_application_error(-20005, 'User-Defined Exceptions: PUR# is not found.');
            when others then
                raise_application_error(-20010, 'User-Defined Exceptions: PUR# format error. PUR# only contain numbers 0~999999.');
--        
        end;
end;
/
show errors

