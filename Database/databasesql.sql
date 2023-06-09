USE [Demo]
GO
/****** Object:  Table [dbo].[HOC]    Script Date: 4/1/2023 11:36:14 AM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[HOC](
	[mssv] [nchar](10) NULL,
	[mshp] [nchar](10) NULL,
	[nhom] [nchar](10) NULL,
	[diem] [nchar](10) NULL,
	[namhoc] [nchar](10) NULL,
	[hocky] [nchar](10) NULL
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[HOCPHAN]    Script Date: 4/1/2023 11:36:14 AM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[HOCPHAN](
	[mshp] [nchar](10) NULL,
	[tenhp] [nchar](25) NULL,
	[tinchi] [varchar](10) NULL
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[SINHVIENN]    Script Date: 4/1/2023 11:36:14 AM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[SINHVIENN](
	[mssv] [nchar](10) NULL,
	[hoten] [nchar](20) NULL,
	[lop] [nchar](10) NULL,
	[nganh] [nchar](20) NULL,
	[khoa] [nchar](20) NULL,
	[quequan] [nchar](10) NULL,
	[gioitinh] [nchar](10) NULL
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[TAIKHOAN]    Script Date: 4/1/2023 11:36:14 AM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[TAIKHOAN](
	[taikhoan] [nchar](20) NULL,
	[matkhau] [nchar](20) NULL
) ON [PRIMARY]
GO
