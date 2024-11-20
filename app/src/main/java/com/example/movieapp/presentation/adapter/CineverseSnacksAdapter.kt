import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.bumptech.glide.request.RequestOptions
import com.example.movieapp.databinding.CineverseSnacksViewholderBinding
import com.example.movieapp.domain.model.CineverseFoodModel
import com.example.movieapp.presentation.viewmodel.CineverseSnackAndTicketViewModel

class CineverseSnacksAdapter(
    private val foodlist: List<CineverseFoodModel>,
    private val viewModel: CineverseSnackAndTicketViewModel
) : RecyclerView.Adapter<CineverseSnacksAdapter.CineverseSnacksViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CineverseSnacksViewHolder {
        val binding = CineverseSnacksViewholderBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return CineverseSnacksViewHolder(binding)
    }

    override fun onBindViewHolder(holder: CineverseSnacksViewHolder, position: Int) {
        holder.bind(foodlist[position])
    }

    override fun getItemCount(): Int = foodlist.size

    inner class CineverseSnacksViewHolder(private val binding: CineverseSnacksViewholderBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(snack: CineverseFoodModel) {
            binding.apply {
                snacksNameTxt.text = snack.foodName
                snacksPriceTxt.text = "$${snack.foodPrice}"
                snacksCountTxt.text = snack.quantity.toString()

                Glide.with(root.context)
                    .load(snack.foodPic)
                    .apply(RequestOptions().transform(CenterCrop()))
                    .into(snacksPic)

                addBtn.setOnClickListener {
                    addBtn.visibility = View.GONE
                    quantityLayout.visibility = View.VISIBLE
                    snack.quantity = 1
                    snacksCountTxt.text = snack.quantity.toString()
                    viewModel.updateSnackQuantity(snack, snack.quantity)
                    viewModel.snackListTotalPrice(calculateTotalPrice())

                }

                increaseBtn.setOnClickListener {
                    if (snack.quantity < 10) {
                        snack.quantity++
                        snacksCountTxt.text = snack.quantity.toString()
                        viewModel.updateSnackQuantity(snack, snack.quantity)
                        viewModel.snackListTotalPrice(calculateTotalPrice())
                    }
                }

                decreaseBtn.setOnClickListener {
                    if (snack.quantity > 1) {
                        snack.quantity--
                        snacksCountTxt.text = snack.quantity.toString()
                        viewModel.updateSnackQuantity(snack, snack.quantity)
                        viewModel.snackListTotalPrice(calculateTotalPrice())
                    } else {
                        viewModel.removeSnack(snack)
                        addBtn.visibility = View.VISIBLE
                        quantityLayout.visibility = View.GONE
                        viewModel.snackListTotalPrice(calculateTotalPrice())
                    }
                }
            }
        }
    }

    private fun calculateTotalPrice(): Double {
        return foodlist.filter { it.quantity >= 1 }.sumOf { it.foodPrice * it.quantity }
    }
}

