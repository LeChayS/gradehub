# Account Management UI Documentation

## Overview
The Account Management page provides a comprehensive interface for monitoring and managing student and lecturer accounts in the GradeHub system.

## Features

### 1. Statistics Dashboard
- **Student Count**: Total number of student accounts
- **Lecturer Count**: Total number of lecturer accounts
- **Active Accounts**: Number of activated accounts
- **Inactive Accounts**: Number of deactivated accounts

### 2. Tabbed Interface
- **Students Tab**: Manage student accounts
- **Lecturers Tab**: Manage lecturer accounts

### 3. Search and Filtering
Each tab includes:
- **Search Field**: Text search for names, emails, or codes
- **Major Filter**: Filter by academic major
- **Status Filter**: Filter by account status (Active/Inactive)
- **Search Button**: Execute search with current filters

### 4. Data Tables

#### Students Table Columns:
- ID (Database ID)
- Student Code (Mã SV)
- Full Name (Họ tên)
- Email
- Major (Ngành)
- Specialization (Chuyên ngành)
- Status (Trạng thái)
- Actions (Thao tác)

#### Lecturers Table Columns:
- ID (Database ID)
- Lecturer Code (Mã GV)
- Full Name (Họ tên)
- Email
- Department (Bộ môn)
- Major (Ngành)
- Status (Trạng thái)
- Actions (Thao tác)

### 5. Action Buttons
- **Add Student/Lecturer**: Create new accounts
- **Export Excel**: Export data to Excel format
- **View/Edit/Delete**: Row-level actions for each account

### 6. Pagination
- Page size selection (10, 25, 50, 100 rows per page)
- Navigation controls (Previous/Next page)
- Current page indicator
- Total pages display

## UI Components

### File Structure
```
src/main/resources/
├── view/admin/
│   └── account-content-view.fxml    # Main account management UI
└── style/
    └── dashboard-view.css           # Styling for account management
```

### CSS Classes
- `.stat-card`: Statistics cards styling
- `.account-tab-pane`: Tab pane styling
- `.search-button`: Search button styling
- `.add-button`: Add new account button
- `.export-button`: Export button styling
- `.table-view`: Data table styling
- `.pagination-button`: Pagination controls
- `.action-button`: Action buttons (view, edit, delete)
- `.status-active/inactive`: Status indicators

## Design Principles

### 1. Responsive Design
- Adapts to different screen sizes
- Maintains usability on smaller screens

### 2. Modern UI
- Clean, professional appearance
- Consistent with existing dashboard design
- Hover effects and visual feedback

### 3. User Experience
- Intuitive navigation with tabs
- Clear visual hierarchy
- Consistent button styling and placement

### 4. Accessibility
- High contrast colors
- Clear typography
- Logical tab order

## Integration Points

### Controller Integration
The UI is designed to work with `AdminDashboardController` and includes:
- FXML field bindings for all interactive elements
- Event handlers for buttons and table actions
- Data binding for statistics and table data

### Future Enhancements
- Real-time data updates
- Bulk operations (select multiple accounts)
- Advanced filtering options
- Account status management
- Password reset functionality
- Account activity logs

## Usage Instructions

1. **Navigate to Account Management**: Click "Quản lý tài khoản" in the admin sidebar
2. **View Statistics**: Check the statistics cards at the top
3. **Switch Tabs**: Use the Students/Lecturers tabs to view different account types
4. **Search**: Use the search field to find specific accounts
5. **Filter**: Use dropdown filters to narrow results
6. **Manage Accounts**: Use action buttons to view, edit, or delete accounts
7. **Export Data**: Click export button to download data as Excel

## Technical Notes

- Built with JavaFX FXML
- Follows existing project structure and naming conventions
- Uses consistent styling with the main dashboard
- Prepared for backend integration with proper field bindings
- Responsive design with CSS media queries
