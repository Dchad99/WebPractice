const $table = $("#table");
const tableLoad = '/web-store/products';
const tableSearch = '/web-store/products/search';

function renderTable(uri, data) {
    $.post(uri).done((entries) => {
        let res;

        console.log("Search table uri => " + data);
        console.log("Table uri => " + entries);
        if(data != null){
            res = data
        } else {
            res = entries;
        }

        console.log(`Result ==> ${res}`);
        let entryViews = ``;

        $.each(res, (key, entry) => {
            const {id, name, date, description, price} = entry;

            entryViews += `<tr data-product="${id}">
                            <td><span class="product_id immutable">${id}</span><input type="text" name="id" disabled style="display: none"></td>
                            <td><span class="data product_name">${name}</span><input name="name" class="update" type="text" style="display: none"></td>
                            <td><span class="data product_desc">${description}</span><input name="desc" class="update" type="text" style="display: none"></td>
                            <td><span class="dataproduct_price">${price}</span><input class="update" name="price" type="number" style="display: none"></td>
                            <td><span class="product_date immutable">${date}</span><input type="date" name="date" disabled style="display: none"></td>
                            <td class="action" data-product="${id}">
                                <div class="container_align">
                                    <div class="action_container">
                                        <button class="btn_add_bucket a"><i class="fa fa-cart-plus"></i></button>
                                        <button class="btn_update a"><i class="fa fa-edit"></i></button>
                                        <button class="btn_delete a"><i class="fa fa-trash"></i></i></button>
                                        <button type="submit" class="btn_approve b" style="display:none;"><i class="fa fa-check"></i></button>
                                        <button class="btn_decline b" style="display:none;"><i class="fa fa-times"></i></button>
                                    </div>
                                </div>
                            </td>
                    </tr>`;
        });

        $table.find('tbody').html(entryViews);
    })
}

$(document).ready(function () {
    $(document).on('click', '.btn_decline', e => {
        $(e.currentTarget).closest('tr').find('.a').css({"display": "block"})
        $(e.currentTarget).closest('tr').find('.b').css({"display": "none"});

        const $spans = $(e.currentTarget).closest('tr').find('td span').not('.immutable');
        $spans.each((k, v) => {
            $(v).closest('td').find('input').css({"display": "none"});
            $(v).css({"display": "block"});
        })

    })

    $(document).on('click', '.btn_update', e => {
        $(e.currentTarget).closest('tr').find('.a').css({"display": "none"})
        $(e.currentTarget).closest('tr').find('.b').css({"display": "block"});

        const $spans = $(e.currentTarget).closest('tr').find('td span').not('.immutable');

        $spans.each((k, v) => {
            $(v).closest('td').find('input').val($(v).text());
            $(v).closest('td').find('input').css({"display": "block"});
            $(v).css({"display": "none"});
        })
    });


    $(document).on('click', '.btn_approve', e => {
        const $input = $(e.currentTarget).closest('tr').find('.update');
        const id = $(e.currentTarget).parents('.action').closest('tr').data('product');
        console.log(id);
        let input = {};

        $.each($input, (key, {name, value}) => {
            input[name] = value
        });

        $.post("/web-store/products/update", {...input, id}).done(() => {
            renderTable(tableLoad, null);
        });

        $(e.currentTarget).closest('tr').find('.a').css({"display": "block"})
        $(e.currentTarget).closest('tr').find('.b').css({"display": "none"});

        const $spans = $(e.currentTarget).closest('tr').find('td span').not('.immutable');
        $spans.each((k, v) => {
            $(v).closest('td').find('input').css({"display": "none"});
            $(v).css({"display": "block"});
        })
    })


    $(document).on('click', '.btn_delete', e => {
        const id = $(e.currentTarget).closest('tr').data('product');
        $.post("/web-store/products/delete", {id}).done(() => {
            renderTable(tableLoad, null);
        });

    })

    $(document).on('click', '.addProduct', e => {
        $('.product_container').show();
        $('.wrapper').css({"filter":"blur(8px)"});

    })

    $("#addProductForm").submit(e => {
        e.preventDefault();
        const inputData = $(e.currentTarget).serializeArray();

        let input = {};
        $.each(inputData, (key, {name, value}) => input[name] = value);

        $.post("/web-store/products/add", {...input}).done(() => {
            renderTable(tableLoad, null);
        })
        $('.close_window').trigger('click');
    })

    $(document).on('click', '.close_window', e => {
        $('.product_container').hide();
        $('.wrapper').css({"filter":"blur(0px)"});
    })

    $(".tableSearch").on('keyup', (e) => {
        const inputData = $(e.currentTarget).val();

        $.post(tableSearch, {"search": inputData}).done((data) => {
            console.log('Data => ' + data);
            renderTable(tableSearch, data);
        })
    })

    renderTable(tableLoad, null);
});