//package com.example.a.enrichedday.adapter;
//
//import android.content.Context;
//import android.content.Intent;
//import android.support.v7.widget.GridLayoutManager;
//import android.support.v7.widget.RecyclerView;
//import android.view.LayoutInflater;
//import android.view.View;
//import android.view.ViewGroup;
//import android.widget.ImageView;
//import android.widget.TextView;
//
//import com.bumptech.glide.Glide;
//import com.example.a.enrichedday.R;
//import com.example.a.enrichedday.bean.PixivBean;
//import com.example.a.enrichedday.ui.PhotoActivity;
//import com.example.a.enrichedday.util.KeyView;
//
//import java.util.List;
//
//
//public class EverydayAdapter extends RecyclerView.Adapter<EverydayAdapter.ViewHolder> {
//    private static final int TYPE_SLIDER = 0; //轮播图
//    private static final int TYPE_TITLE = 1; // 标题
//    private static final int TYPE_TWO = 2;// 二张图
//    private static final int TYPE_THREE = 3;// 三张图
//
//
//    private List<KeyView> keyViewList;
//    private Context mContext;
//    static class ViewHolder extends RecyclerView.ViewHolder {
//        ImageView imageView;
//        public ViewHolder(View view) {
//            super(view);
//            imageView = (ImageView) view.findViewById(R.id.pixiv_image);
//        }
//
//    }
//    private class SilerHolder extends RecyclerView.ViewHolder {
//        ImageView imageView;
//        public SilerHolder(View view) {
//            super(view);
//            imageView = (ImageView) view.findViewById(R.id.pixiv_image);
//        }
//    }
//    private class BangumiHolder extends RecyclerView.ViewHolder {
//        ImageView imageView;
//        TextView titleView;
//        public BangumiHolder(View view) {
//            super(view);
//            imageView = (ImageView) view.findViewById(R.id.bangumi_image);
//            titleView = (TextView) view.findViewById(R.id.bangumi_title);
//        }
//    }
//    private class ComicHolder extends RecyclerView.ViewHolder {
//        ImageView imageView;
//        TextView titleView;
//        public ComicHolder(View view) {
//            super(view);
//            imageView = (ImageView) view.findViewById(R.id.bangumi_image);
//            titleView = (TextView) view.findViewById(R.id.bangumi_title);
//        }
//
//    }
//    private class PixivHolder extends RecyclerView.ViewHolder {
//        ImageView imageView;
//        public PixivHolder(View view) {
//            super(view);
//            imageView = (ImageView) view.findViewById(R.id.pixiv_image);
//        }
//
//    }
//
//
//
//
//    public EverydayAdapter(List<KeyView> s) {
//        keyViewList = s;
//    }
//
//
//
//    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
//        if(mContext == null) {
//            mContext = parent.getContext();
//        }
//        switch (viewType) {
//            case TYPE_SLIDER:
//                View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.pivix_item,parent,false);
//                SilerHolder holder = new SilerHolder(view);
//                return holder;
//                break;
//            case TYPE_TITLE:
//                return new BangumiHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_bangumi,parent,false));
//                break;
//            case TYPE_TWO:
//                return new BangumiHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_bangumi,parent,false));
//                break;
//            case TYPE_THREE:
//                return new BangumiHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_bangumi,parent,false));
//                break;
//            default:
//                return new BangumiHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_bangumi,parent,false));
//            break;
//
//        }
//
//
//
//
//        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.pivix_item,parent,false);
//        final ViewHolder holder = new ViewHolder(view);
////        holder.imageView.setOnClickListener(new View.OnClickListener() {
////            @Override
////            public void onClick(View v) {
////                int position = holder.getAdapterPosition();
////                PixivBean.PictureBean b = pixivBeanList.get(position-1);
////                //Xrecycler刷新部分占了一个位置
////                Intent intent = new Intent(mContext, PhotoActivity.class);
////                intent.putExtra("imageUrl","http://kyoko.b0.upaiyun.com/pixiv-ranking/"+b.picUrl);
////                mContext.startActivity(intent);
////
////            }
////        });
//
//        return holder;
//
//    }
//
//
//
//    @Override
//    public int getItemCount() {
//        return keyViewList.size();
//    }
//
//
//    @Override
//    public int getItemViewType(int position) {
//        switch (keyViewList.get(position).getKey()){
//            case 1:
//                return TYPE_TITLE;
//            case 2:
//                return TYPE_TWO;
//            case 3:
//                return TYPE_THREE;
//            default:
//                return 0;
//        }
//
//    }
//
//    public void onBindViewHolder(ViewHolder holder, int position) {
//        PixivBean.PictureBean pictureBean = pixivBeanList.get(position);
//        Glide.with(mContext)
//                .load("http://kyoko.b0.upaiyun.com/pixiv-ranking/"+pictureBean.picUrl)
//                .into(holder.imageView);
//
//    }
//
//    @Override
//    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
//        super.onAttachedToRecyclerView(recyclerView);
//        RecyclerView.LayoutManager manager = recyclerView.getLayoutManager();
//        if(manager instanceof GridLayoutManager) {
//            final GridLayoutManager gridManager = ((GridLayoutManager) manager);
//            gridManager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
//                @Override
//                public int getSpanSize(int position) {
//                    int type = getItemViewType(position);
//                    switch (type){
//                        case TYPE_TITLE:
//                            return 1;
//                        case TYPE_TWO:
//                            return 2;
//                        case TYPE_THREE:
//                            return 3;
//                        case TYPE_SLIDER:
//                            return 1;
//                        default:
//                            return 1;
//                    }
//                }
//            });
//        }
//
//    }
//}
