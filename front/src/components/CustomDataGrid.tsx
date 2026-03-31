import EditIcon from '@mui/icons-material/Edit';
import { Box } from '@mui/material';
import {
    DataGrid,
    GridActionsCellItem,
    type GridColDef,
    type GridPaginationModel,
    type GridSortModel,
} from '@mui/x-data-grid';
import * as React from 'react';
import type { Page } from '../common/types/pageable';
import type { UUID } from '../common/types/uuid';

export interface DataGridRowAction<T> {
    id: string;
    label: string;
    onClick: (row: T) => void;
    icon?: React.ReactNode;
    disabled?: boolean | ((row: T) => boolean);
}

interface CustomDataGridProps<T extends { id: string | number; }> {
    page: Page<T>;
    columns: GridColDef<T>[];
    loading?: boolean;

    paginationModel: GridPaginationModel;
    onPaginationModelChange: (model: GridPaginationModel) => void;

    sortModel: GridSortModel;
    onSortModelChange: (model: GridSortModel) => void;

    rowActions?: DataGridRowAction<T>[];

    getRowId?: (row: T) => string | number;
    pageSizeOptions?: number[];
    height?: number | string;
}

const CustomDataGrid = <T extends { id: UUID; }>({
    page,
    columns,
    loading = false,
    paginationModel,
    onPaginationModelChange,
    sortModel,
    onSortModelChange,
    rowActions = [],
    getRowId,
    pageSizeOptions = [5, 10, 20, 50],
    height = 600,
}: CustomDataGridProps<T>) => {

    const actionColumn = React.useMemo<GridColDef<T> | null>(() => {
        if (!rowActions.length) return null;
        return {
            field: '__actions__',
            type: 'actions',
            headerName: '',
            width: 80,
            sortable: false,
            filterable: false,
            disableColumnMenu: true,
            getActions: (params) =>
                rowActions.map((action) => {
                    const disabled =
                        typeof action.disabled === 'function'
                            ? action.disabled(params.row)
                            : !!action.disabled;

                    return (
                        <GridActionsCellItem
                            key={action.id}
                            icon={action.icon ?? <EditIcon />}
                            label={action.label}
                            onClick={() => action.onClick(params.row)}
                            showInMenu
                            disabled={disabled}
                        />
                    );
                }),
        };
    }, [rowActions]);

    const finalColumns = React.useMemo(() => {
        return actionColumn ? [...columns, actionColumn] : columns;
    }, [columns, actionColumn]);

    return (
        <Box sx={{ height, width: '100%' }}>
            <DataGrid
                rows={page.content}
                columns={finalColumns}
                loading={loading}
                getRowId={getRowId}
                rowCount={page.totalElements}
                paginationMode="server"
                sortingMode="server"
                paginationModel={paginationModel}
                onPaginationModelChange={onPaginationModelChange}
                sortModel={sortModel}
                onSortModelChange={onSortModelChange}
                pageSizeOptions={pageSizeOptions}
                disableColumnFilter
                disableColumnSelector
                disableDensitySelector
                disableRowSelectionOnClick
            />
        </Box>
    );
};

export default CustomDataGrid;
