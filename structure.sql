--1
create database db_library_management;


create table borrow_cards(
    card_id serial primary key ,
    book_title varchar(150) not null,
    borrower_name varchar(100) not null ,
    borrow_date timestamp not null,
    return_deadline timestamp not null,
    quantity int not null ,
    status varchar(30) not null
);



 --2
 -- function: lấy danh sách tất cả các phiếu mượn
 create or replace function fn_get_all_borrow_cards()
 returns table(
    card_id int,
    book_title varchar,
    borrower_name varchar ,
    borrow_date timestamp ,
    return_deadline timestamp ,
    quantity int  ,
    status varchar
) as $$
begin
    return query select * from borrow_cards order by card_id desc ;
end;
$$ language plpgsql;
-- procedure: thêm 1 phiếu mượn
create or replace procedure sp_add_borrow_card(
    p_book_title varchar,
    p_borrower_name varchar ,
    p_borrow_date timestamp ,
    p_return_deadline timestamp ,
    p_quantity int  ,
    p_status varchar
)as $$
    begin
        insert into  borrow_cards(book_title, borrower_name, borrow_date, return_deadline, quantity, status)
        values (p_book_title,p_borrower_name,p_borrow_date,p_return_deadline,p_quantity,p_status);
    end;
    $$language plpgsql;
-- function: lấy danh sách phiếu mượn theo tên độc giả
create or replace function fn_get_borrow_cards_by_borrower(p_borrower_name varchar)
returns table(
                 card_id int,
                 book_title varchar,
                 borrower_name varchar ,
                 borrow_date timestamp ,
                 return_deadline timestamp ,
                 quantity int  ,
                 status varchar
             ) as $$
    begin
        return query
        select * from borrow_cards
        where lower(borrow_cards.borrower_name) like lower('%'||p_borrower_name||'%');

    end;
    $$ language plpgsql;
-- procedure: cập nhật thông tin phiếu mượn theo card_id
create or replace procedure sp_update_brrow_card(
    p_card_id int,
    p_book_title varchar,
    p_borrower_name varchar ,
    p_borrow_date timestamp ,
    p_return_deadline timestamp ,
    p_quantity int  ,
    p_status varchar
)as $$
    begin
        update borrow_cards
        set book_title=p_book_title,
            borrower_name=p_borrower_name,
            borrow_date=p_borrow_date,
            return_deadline=p_return_deadline,
            quantity=p_quantity,
            status=p_status
        where card_id=p_card_id;
    end;
    $$language plpgsql;
--xóa phiếu mượn theo card_id
create or replace procedure sp_delete_borrow_card(p_card_id int)
as $$
    begin
        delete from borrow_cards where card_id=p_card_id;
    end;
    $$language plpgsql;
-- function: tìm kiếm phiếu mượn theo tên book_title
create or replace function  fn_search_borrow_cards_by_book_title (p_book_title varchar)
returns table(
                 card_id int,
                 book_title varchar,
                 borrower_name varchar ,
                 borrow_date timestamp ,
                 return_deadline timestamp ,
                 quantity int  ,
                 status varchar
             )as $$
    begin
        return query
        select * from borrow_cards
        where lower(borrow_cards.book_title) like lower('%'||p_book_title||'%');
    end;
    $$language plpgsql;
