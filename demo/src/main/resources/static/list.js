'use strict';

$(function () {
    $('#childCategory').on('click', function () {
        let hostUrl = 'http://localhost:8080/list/serchChildCategory';
        let selectedOption = $('#parentCategory option:selected').val();
        $.ajax({
            url: hostUrl,
            type: 'post',
            dataType: 'json',
            data: { parentCategory: selectedOption },
            async: true,
        }).done(function (data) {
            var childCategoryList = [];
            if (data.length == 0) {
                childCategoryList = "<option> -- childCategory -- </option>";
                console.log(childCategoryList);
            } else {
            for (var i = 0; i < data.length; i++) {
                var child = "<option> " + data[i].categoryName + "</option>";
                childCategoryList.push(child);
            }
            $('#childCategory').html(childCategoryList)
        }
            console.log(data);

        })
    });
});

$(function () {
    $('#grandChildCategory').on('click', function () {
        let hostUrl = 'http://localhost:8080/item/searchGrandChildCategory';
        let selectedOption = $('#parentCategory option:selected').val();
        let childSelectedOption = $('#childCategory option:selected').val();
        $.ajax({
            url: hostUrl,
            type: 'post',
            dataType: 'json',
            data: {
                parentCategory: selectedOption,
                childCategory: childSelectedOption
            },
            async: true,
        }).done(function (data) {
            var grandChildCategoryList = [];
            if (data.length == 0) {
                grandChildCategoryList = "<option> -- grandChildCategory -- </option>";
            } else {
                for (var i = 0; i < data.length; i++) {
                    var grandChild = "<option> " + data[i].categoryName + "</option>";
                    grandChildCategoryList.push(grandChild);
                }
            }
            $('#grandChildCategory').html(grandChildCategoryList)
            console.log(data);


        })
    });
});


const itemInfo = sessionStorage.getItem("itemInfo");
console.log(itemInfo);
if (itemInfo != null) {
    const itemInfoArry = itemInfo.split(',');
    console.log(itemInfoArry);
    if (itemInfoArry.length != 0) {
        const itemName = itemInfoArry[0];
        const parentCategory = itemInfoArry[1];
        const childCategory = itemInfoArry[2];
        const grandChildCategory = itemInfoArry[3];
        const brand = itemInfoArry[4];
        console.log(itemName);
        console.log(childCategory);
        document.getElementById('name').value = itemName;
        document.getElementById('parentCategory').value = parentCategory;
        document.getElementById('childCategory').value = childCategory;
        document.getElementById('grandChildCategory').value = grandChildCategory;
        document.getElementById('brand').value = brand;
    }
}

$(function () {
    $('.navbar-brand').on('click', function () {
        sessionStorage.removeItem('itemInfo');

    })
})


$(function () {
    $('#forms').on('submit', function () {
        let itemInfo = [];
        $('.form-control').each(function (i, element) {
            console.log($(element).val());
            itemInfo.push($(element).val());
        })

        sessionStorage.setItem('itemInfo', itemInfo);

    })


})
