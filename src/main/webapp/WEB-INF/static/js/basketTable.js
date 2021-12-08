const basketUri = "/products/cart";
const $table = $("#table");

function renderTable(uri) {
    $.get(uri).done((entries) => {
        let entryViews = ``;

        $.each(entries, (key, entry) => {
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
                                        <button class="btn_update_b a"><i class="fa fa-check"></i></button>
                                        <button class="btn_delete_b a"><i class="fa fa-trash"></i></i></button>
                                    </div>
                                </div>
                            </td>
                    </tr>`;
        });

        $table.find('tbody').html(entryViews);
    })
}

$(document).ready(() => {
    $(document).on('click', '.btn_delete_b', e => {
        const id = $(e.currentTarget).closest('tr').data('product');
        $.post(`/products/cart/delete/${id}`).done(() => {
            renderTable(basketUri);
        })
    })

    $(document).on('click', '.btn_product', e => {
        $.get("/productPage").done(() => {
            console.log("Product List")
        })
    });

    renderTable(basketUri);
})