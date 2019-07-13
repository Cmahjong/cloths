package com.yj.clothsdemo.adapter

import android.graphics.Color
import android.view.View
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.yj.clothsdemo.R
import com.yj.service.response.Data6
import kotlinx.android.synthetic.main.item_price_detail.view.*

/**
 * desc:
 * time: 2019/3/21
 * @author yinYin
 */
class PriceDetailAdapter:BaseQuickAdapter<Data6, BaseViewHolder>(R.layout.item_price_detail)  {
    override fun convert(helper: BaseViewHolder?, item: Data6?) {
        helper?.itemView?.apply {
            if (item?.className.isNullOrBlank()) {
                tv_type_one.setTextColor(Color.parseColor("#989898"))
                tv_type_one.text = "选择大类"
            } else {
                tv_type_one.setTextColor(Color.parseColor("#353535"))
                tv_type_one.text = item?.className ?: ""
            }
            if (item?.detailName.isNullOrBlank()) {
                tv_type_two.setTextColor(Color.parseColor("#989898"))
                tv_type_two.text = "选择小类"
            } else {
                tv_type_two.setTextColor(Color.parseColor("#353535"))
                tv_type_two.text = item?.detailName ?: ""
            }
            if (item?.number.isNullOrBlank()) {
                tv_type_three.setTextColor(Color.parseColor("#989898"))
                tv_type_three.text = "数量"
            } else {
                tv_type_three.setTextColor(Color.parseColor("#353535"))
                tv_type_three.text = item?.number ?: ""
            }

            if (helper.adapterPosition == data.size - 1) {
                tv_delete.visibility = View.INVISIBLE
            } else {
                tv_delete.visibility = View.VISIBLE
            }

        }

        helper?.addOnClickListener(R.id.tv_delete)
        helper?.addOnClickListener(R.id.rl_type_one)
        helper?.addOnClickListener(R.id.rl_type_two)
        helper?.addOnClickListener(R.id.rl_type_three)
    }
}