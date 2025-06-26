use gradehubdb;

-- 1. Người dùng
CREATE TABLE nguoi_dung (
    id INT AUTO_INCREMENT PRIMARY KEY,
    email VARCHAR(100) UNIQUE NOT NULL,
    mat_khau_hash VARCHAR(255) NOT NULL,
    vai_tro ENUM('sinh_vien', 'giang_vien', 'quan_tri') NOT NULL,
    ngay_tao DATETIME DEFAULT CURRENT_TIMESTAMP
);

-- 2. Ngành
CREATE TABLE nganh (
    ma_nganh VARCHAR(10) PRIMARY KEY,
    ten_nganh VARCHAR(100) UNIQUE NOT NULL
);

-- 3. Chuyên ngành
CREATE TABLE chuyen_nganh (
    ma_chuyen_nganh VARCHAR(10) PRIMARY KEY,
    ma_nganh VARCHAR(10) NOT NULL,
    ten_chuyen_nganh VARCHAR(100),
    FOREIGN KEY (ma_nganh) REFERENCES nganh(ma_nganh)
);

-- 4. Sinh viên
CREATE TABLE sinh_vien (
    ma_sinh_vien VARCHAR(20) PRIMARY KEY,
    id_nguoi_dung INT UNIQUE NOT NULL,
    ho_ten VARCHAR(100),
    ngay_sinh DATE,
    gioi_tinh ENUM('nam', 'nu', 'khac'),
    ma_nganh VARCHAR(10) NOT NULL,
	ma_chuyen_nganh VARCHAR(10),
    FOREIGN KEY (id_nguoi_dung) REFERENCES nguoi_dung(id),
    FOREIGN KEY (ma_nganh) REFERENCES nganh(ma_nganh),
    FOREIGN KEY (ma_chuyen_nganh) REFERENCES chuyen_nganh(ma_chuyen_nganh)
);

-- 5. Giảng viên
CREATE TABLE giang_vien (
    ma_giang_vien VARCHAR(20) PRIMARY KEY,
    id_nguoi_dung INT UNIQUE NOT NULL,
    ho_ten VARCHAR(100),
    bo_mon VARCHAR(100),
    ma_nganh VARCHAR(10) NOT NULL,
    FOREIGN KEY (id_nguoi_dung) REFERENCES nguoi_dung(id),
    FOREIGN KEY (ma_nganh) REFERENCES nganh(ma_nganh)
);

-- 6. Môn học
CREATE TABLE mon_hoc (
    ma_mon_hoc VARCHAR(20) PRIMARY KEY,
    ten_mon_hoc VARCHAR(100) NOT NULL,
    tin_chi_ly_thuyet INT CHECK (tin_chi_ly_thuyet >= 2),
    tin_chi_thuc_hanh INT CHECK (tin_chi_thuc_hanh IN (0, 1)) DEFAULT 0,
    loai_mon ENUM('ly_thuyet', 'ly_thuyet_va_thuc_hanh') NOT NULL
);

-- 7. Môn học theo ngành
CREATE TABLE mon_hoc_nganh (
	id INT AUTO_INCREMENT PRIMARY KEY,
    ma_mon_hoc VARCHAR(20) NOT NULL,
    ma_nganh VARCHAR(10) NOT NULL,
    ma_chuyen_nganh VARCHAR(10), -- optional: nếu là môn chung thì NULL
    la_chuyen_nganh BOOLEAN DEFAULT FALSE,
    nam_ap_dung INT NOT NULL,
    FOREIGN KEY (ma_mon_hoc) REFERENCES mon_hoc(ma_mon_hoc),
    FOREIGN KEY (ma_nganh) REFERENCES nganh(ma_nganh),
    FOREIGN KEY (ma_chuyen_nganh) REFERENCES chuyen_nganh(ma_chuyen_nganh)
);

-- 8. Trọng số điểm
CREATE TABLE trong_so_diem (
    id INT AUTO_INCREMENT PRIMARY KEY,
    ma_mon_hoc VARCHAR(20) NOT NULL,
    hs_chuyen_can FLOAT NOT NULL,
    hs_giua_ky FLOAT NOT NULL,
    hs_cuoi_ky FLOAT NOT NULL,
    FOREIGN KEY (ma_mon_hoc) REFERENCES mon_hoc(ma_mon_hoc)
);

-- 9. Lớp học phần
CREATE TABLE lop_hoc_phan (
    ma_lop_hoc_phan VARCHAR(8) PRIMARY KEY, -- vd: 24100001
    ma_mon_hoc VARCHAR(20) NOT NULL,
    ma_giang_vien VARCHAR(20) NOT NULL,
    hoc_ky VARCHAR(20) NOT NULL,
    nam_hoc INT NOT NULL,
    lop_hoc VARCHAR(50),
    thu_trong_tuan INT CHECK (thu_trong_tuan BETWEEN 2 AND 7),
    tiet_bat_dau INT CHECK (tiet_bat_dau BETWEEN 1 AND 12),
    tiet_ket_thuc INT CHECK (tiet_ket_thuc BETWEEN 1 AND 12),
    phong_hoc VARCHAR(50),
    FOREIGN KEY (ma_mon_hoc) REFERENCES mon_hoc(ma_mon_hoc),
    FOREIGN KEY (ma_giang_vien) REFERENCES giang_vien(ma_giang_vien)
);

-- 10. Gán sinh viên vào lớp học phần
CREATE TABLE sinh_vien_lop_hoc_phan (
    id INT AUTO_INCREMENT PRIMARY KEY,
    ma_sinh_vien VARCHAR(20) NOT NULL,
    ma_lop_hoc_phan VARCHAR(8) NOT NULL,
    FOREIGN KEY (ma_sinh_vien) REFERENCES sinh_vien(ma_sinh_vien),
    FOREIGN KEY (ma_lop_hoc_phan) REFERENCES lop_hoc_phan(ma_lop_hoc_phan)
);

-- 11. Điểm sinh viên
CREATE TABLE diem_sinh_vien (
    id INT AUTO_INCREMENT PRIMARY KEY,
    ma_sinh_vien VARCHAR(20) NOT NULL,
    ma_lop_hoc_phan VARCHAR(8) NOT NULL,
    diem_chuyen_can FLOAT CHECK (diem_chuyen_can BETWEEN 0 AND 10),
    diem_giua_ky FLOAT CHECK (diem_giua_ky BETWEEN 0 AND 10) NOT NULL,
    diem_cuoi_ky FLOAT CHECK (diem_cuoi_ky BETWEEN 0 AND 10) NOT NULL,
    diem_hoc_phan FLOAT,
    diem_chu ENUM('A+','A','B+','B','C+','C','D+','D','F'),
    FOREIGN KEY (ma_sinh_vien) REFERENCES sinh_vien(ma_sinh_vien),
    FOREIGN KEY (ma_lop_hoc_phan) REFERENCES lop_hoc_phan(ma_lop_hoc_phan)
);

-- 12. Tổng kết học kỳ
CREATE TABLE tong_ket_hoc_ky (
    id INT AUTO_INCREMENT PRIMARY KEY,
    ma_sinh_vien VARCHAR(20) NOT NULL,
    hoc_ky VARCHAR(20),
    nam_hoc INT,
    diem_tb_hoc_phan FLOAT,
    tong_tin_chi INT,
    tong_tin_chi_dat INT,
    FOREIGN KEY (ma_sinh_vien) REFERENCES sinh_vien(ma_sinh_vien)
);

