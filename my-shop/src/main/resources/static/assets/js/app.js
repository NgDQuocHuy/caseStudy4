class App {

    static DOMAIN_API = "http://localhost:9095";
    static BASE_URL_Product = this.DOMAIN_API + "/api/products";
    static BASE_URL_User = this.DOMAIN_API + "/api/users";
    static BASE_URL_AUTH = this.DOMAIN_API + "/api/auth";
    static BASE_URL_IMAGE = "https://res.cloudinary.com/ngdquochuy241/image/upload/f_auto,c_scale,w_250/image_images"
    static BASE_URL_AVATAR = "https://res.cloudinary.com/ngdquochuy241/image/upload/f_auto,c_scale,w_250/avatar_images"

    static AlertMessageEn = class {
        static SUCCESS_CREATED = "Successful data generation !";
        static SUCCESS_UPDATED = "Data update successful !";
        static SUCCESS_DEPOSIT = "Deposit transaction successful !";
        static SUCCESS_WITHDRAW = "Withdrawal transaction successful !";
        static SUCCESS_TRANSFER = "Transfer transaction successful !";
        static SUCCESS_DEACTIVATE = "Deactivate the customer successfully !";

        static ERROR_400 = "The operation failed, please check the data again.";
        static ERROR_401 = "Unauthorized - Your access token has expired or is not valid.";
        static ERROR_403 = "Forbidden - You are not authorized to access this resource.";
        static ERROR_404 = "Not Found - The resource has been removed or does not exist";
        static ERROR_500 = "Internal Server Error - The server system is having problems or cannot be accessed.";

        static ERROR_LOADING_PROVINCE = "Loading list of provinces - cities failed !";
        static ERROR_LOADING_DISTRICT = "Loading list of district - ward failed !"
        static ERROR_LOADING_WARD = "Loading list of wards - communes - towns failed !";
    }

    static AlertMessageVi = class {
        static SUCCESS_CREATED = "Tạo dữ liệu thành công !";
        static SUCCESS_UPDATED = "Cập nhật dữ liệu thành công !";
        static SUCCESS_DEPOSIT = "Giao dịch gửi tiền thành công !";
        static SUCCESS_WITHDRAW = "Giao dịch rút tiền thành công !";
        static SUCCESS_TRANSFER = "Giao dịch chuyển khoản thành công !";
        static SUCCESS_DEACTIVATE = "Hủy kích hoạt khách hàng thành công !";
        static SUCCESS_SUSPENDED = "Tài khoản đã bị chặn khởi hệ thống thành công !";

        static ERROR_CREATED = "Tạo dữ liệu mới không thành công !";
        static ERROR_WITHDRAW = "Số tiền trong tài khoản của quý khách không đủ, vui lòng kiểm tra lại.";
        static ERROR_400 = "Thao tác không thành công, vui lòng kiểm tra lại dữ liệu.";
        static ERROR_401 = "Unauthorized - Access Token của bạn hết hạn hoặc không hợp lệ.";
        static ERROR_403 = "Forbidden - Bạn không được quyền truy cập tài nguyên này.";
        static ERROR_404 = "Not Found - Tài nguyên này đã bị xóa hoặc không tồn tại";
        static ERROR_500 = "Internal Server Error - Hệ thống Server đang có vấn đề hoặc không truy cập được.";

        static ERROR_LOADING_PROVINCE = "Tải danh sách tỉnh - thành phố không thành công !";
        static ERROR_LOADING_DISTRICT = "Tải danh sách quận - phường - huyện không thành công !";
        static ERROR_LOADING_WARD = "Tải danh sách phường - xã - thị trấn không thành công !";
    }

    static SweetAlert = class {

        static showAlertSuccess(t) {
            Swal.fire({
                position: 'top-end',
                icon: 'success',
                title: t,
                showConfirmButton: false,
                timer: 1500
            })
        }

        static showAlertError(t) {
            Swal.fire({
                position: 'center',
                icon: 'error',
                title: t,
                showConfirmButton: true
            })
        }

        static showSuspendedConfirmDialog() {
            return Swal.fire({
                icon: 'warning',
                text: 'Are you sure to suspend the selected customer ?',
                showCancelButton: true,
                confirmButtonColor: '#3085d6',
                cancelButtonColor: '#d33',
                confirmButtonText: 'Yes, please suspend this client !',
                cancelButtonText: 'Cancel',
            })
        }
    }

    static IziToast = class {
        static showSuccessAlert(m) {
            iziToast.success({
                title: 'OK',
                position: 'topRight',
                timeout: 2500,
                message: m
            });
        }

        static showErrorAlert(m) {
            iziToast.error({
                title: 'Error',
                position: 'topRight',
                timeout: 2500,
                message: m
            });
        }
    }

    static renderCardProduct(product, type) {
        let str = `
            <div id="div_${product.id}" class="card col col-lg-2 " style="margin: 23px">
                <section>
                    <div style="height: 14rem">
                        <img class="card-img-top" src="${App.BASE_URL_IMAGE}/${product.image}" alt="No Image Product" style=" margin-top: 10px; border-radius: 10px">
                    </div>
                    <div class="card-body card-body-centered">
                        <h3 class="card-title">${product.title}</h3>
                        <p class="card-text">${type.type}</p>
                    </div>
                    <div class="card-footer card-body-centered">
                        <h3><strong class="text-danger">${product.price} đ</strong></h3>
                    </div>
                    <div class="card-footer">
                        <button class="btn btn-primary" data-id="${product.id}">Ad</button>
                        <button class="btn btn-secondary edit" data-id="${product.id}">Ed</button>
                        <button class="btn btn-danger delete" data-id="${product.id}">De</button>
                    </div>
                </section>  
            </div>
        `;
        return str;
    }

    static renderUser(user) {
        let str = `<tr>
            <td>
                <p class="font-22 font-weight-light" class="font-22 font-weight-light">${user.id}</p>
            </td>
            <td>
                <div class="overflow-hidden">
                    <p class="mb-0 font-weight-light font-24">${user.username}</p>
                </div>
            </td>
            <td>
                <p class="font-22 font-weight-light">${user.infoUser.fullName}</p>
            </td>
            <td>
                <p class="font-22 font-weight-light">${user.role.code}</p>
            </td>
            <td>
                <p class="font-22 font-weight-light">${user.infoUser.phone}</p>
            </td>
            <td>
                <p class="font-22 font-weight-light">${user.locationRegion.address}</p>
            </td>
        </tr>`;
        return str;
    }

    static renderErrorCre(errors) {
        let str = "<div class='bg-danger' style='margin-top: 20px; padding: 10px 0'><ul>";
        for (let i = 0; i < errors.length; i++) {
            str += `<li><h4>${errors[i]}</h4></li>`;
        }
        str += "</ul></div>";
        return str;
    }

    static renderErrorUp(errors) {
        let str = "<div class='bg-danger' style='margin-top: 20px; padding: 10px 0'><ul>";
        for (let i = 0; i < errors.length; i++) {
            str += `<li><h4>${errors[i]}</h4></li>`;
        }
        str += "</ul></div>";
        return str;
    }
}

class LocationRegion {
    constructor(id, provinceId, provinceName, districtId, districtName, wardId, wardName, address) {
        this.id = id;
        this.provinceId = provinceId;
        this.provinceName = provinceName;
        this.districtId = districtId;
        this.districtName = districtName;
        this.wardId = wardId;
        this.wardName = wardName;
        this.address = address;
    }
}

class Type {
    constructor(id, type) {
        this.id = id;
        this.type = type;
    }
}

class InfoUser {
    constructor(id, fullName, phone) {
        this.id = id;
        this.fullName = fullName;
        this.phone = phone;
    }
}

class Role {
    constructor(id, code) {
        this.id = id;
        this.code = code;
    }
}

class Product {
    constructor(id, title, price, type, image) {
        this.id = id;
        this.title = title;
        this.price = price;
        this.type = type;
        this.image = image;
    }
}

class User {
    constructor(id, username, role, phone, address) {
        this.id = id;
        this.username = username;
        this.role = role;
        this.phone = phone;
        this.address = address;
    }
}

